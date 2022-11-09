package org.example.gallery.repositories.image;

import org.example.gallery.models.Image;
import org.example.gallery.models.Image_;
import org.example.gallery.models.Tag;
import org.example.gallery.models.Tag_;
import org.example.gallery.utils.ImageUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ImageRepositoryImp implements ImageRepository {
    @PersistenceContext
    private EntityManager em;

    @Value("${image.root-path}")
    private String root;

    private static final String FULL_IMAGE_PATH = "/full_image/";
    private static final String THUMBNAIL_IMAGE_PATH = "/thumbnail_image/";

    public Image findById(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);

        Root<Image> root = cq.from(Image.class);

        Predicate idPredicate = cb.equal(root.get(Image_.id), id);
        cq.where(idPredicate);

        TypedQuery<Image> query = em.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public List<Image> findByAttributes(String searchTerm, List<String> tagNames) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);

        Root<Image> root = cq.from(Image.class);


        List<Predicate> predicates = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            Predicate namePredicate = cb.like(root.get(Image_.NAME), "%" + searchTerm + "%");
            Predicate descriptionPredicate = cb.like(root.get(Image_.DESCRIPTION), "%" + searchTerm + "%");
            predicates.add(cb.or(namePredicate, descriptionPredicate));

        }

        if(tagNames != null) {
            SetJoin<Image, Tag> imageTags = root.join(Image_.tags);
            predicates.add(imageTags.get(Tag_.NAME).in(tagNames));
        }

        if(predicates.size() == 0) {
            return null;
        }
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Image> query = em.createQuery(cq);

        return query.getResultList();
    }

    public List<Image> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);

        Root<Image> root = cq.from(Image.class);
        cq.select(root);

        TypedQuery<Image> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void save(Image image, byte[] bytes, String fileName) {
        try {
            String uuid = ImageUtils.generateUUID();
            image.setUuid(uuid);

            byte[] thumbnail = ImageUtils.createThumbnail(bytes);

            image.setFilePath(saveImageLocally(bytes, uuid, fileName, false));
            image.setThumbnailFilePath(saveImageLocally(thumbnail, uuid, fileName, true));
            image.setUploadDate(new Date());

            em.persist(image);
        } catch (Exception e) {
            e.printStackTrace();

            if (image.getFilePath() != null) {
                deleteFile(image.getFilePath());
            }
            if (image.getThumbnailFilePath() != null) {
                deleteFile(image.getThumbnailFilePath());
            }
        }
    }

    @Override
    @Transactional
    public void update(Image image) {
        em.merge(image);
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        Image image = findById(id);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Image> criteriaDelete = cb.createCriteriaDelete(Image.class);

        Root<Image> root = criteriaDelete.from(Image.class);
        criteriaDelete.where(cb.equal(root.get(Image_.id), id));

        em.createQuery(criteriaDelete).executeUpdate();

        return deleteFile(image.getFilePath()) && deleteFile(image.getThumbnailFilePath());
    }

    private String saveImageLocally(byte[] image, String uuid, String fileName, boolean isThumbnail) throws IOException {
        String folder = isThumbnail ? THUMBNAIL_IMAGE_PATH : FULL_IMAGE_PATH;
        String path = root + uuid + folder + fileName;

        new File(root + uuid + folder).mkdirs();
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(image);
        fos.close();

        return path;
    }

    private boolean deleteFile(String path) {
        try {
            File file = new File(path);
            boolean isFileDeleted = deleteFileOrEmptyDirectory(path);

            String directoryPath = file.getParentFile().getAbsolutePath();

            if (isDirectoryEmpty(directoryPath)) {
                deleteFileOrEmptyDirectory(directoryPath);
            }
            return isFileDeleted;

        } catch(SecurityException e) {
            //TODO
        }
        return false;
    }

    private boolean deleteFileOrEmptyDirectory(String path) {
        File directory = new File(path);
        return directory.delete();
    }

    private boolean isDirectoryEmpty(String directory) {
        try (var entries = Files.list(Paths.get(directory))) {
            return entries.count() == 0;
        } catch (IOException e) {
            return false;
        }
    }
}