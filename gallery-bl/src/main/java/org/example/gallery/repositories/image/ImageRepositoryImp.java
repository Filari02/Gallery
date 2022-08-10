package org.example.gallery.repositories.image;

import org.example.gallery.models.*;
import org.example.gallery.repositories.image.ImageRepository;
import org.example.gallery.utils.ImageUtils;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Date;
import java.util.List;


@Repository
public class ImageRepositoryImp implements ImageRepository {

    @PersistenceContext
    private EntityManager em;

    private static final String ROOT = "D:/Gallery/";


    public Image findById(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Image> cq = cb.createQuery(Image.class);

        Root<Image> root = cq.from(Image.class);

        Predicate idPredicate = cb.equal(root.get(Image_.id), id);
        cq.where(idPredicate);

        TypedQuery<Image> query = em.createQuery(cq);
        return query.getSingleResult();
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
    public void save(Image image, byte[] bytes) {

        String uuid = ImageUtils.generateUUID();
        image.setUuid(uuid);
        try {
            byte[] thumbnail = ImageUtils.createThumbnail(bytes);

            image.setFilePath(saveImageLocally(bytes, uuid, image.getName(), false));
            image.setThumbnailFilePath(saveImageLocally(thumbnail, uuid, image.getName(), true));
            image.setUploadDate(new Date());

            em.persist(image);
        } catch (Exception e) {
            e.printStackTrace();
            deleteDirectory(new File(ROOT + uuid));
        }
    }

    @Override
    @Transactional
    public void update(Image image) {
        em.merge(image);
    }

    @Override
    @Transactional
    public void deleteById(int id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Image> criteriaDelete = cb.createCriteriaDelete(Image.class);

        Root<Image> root = criteriaDelete.from(Image.class);
        criteriaDelete.where(cb.equal(root.get(Image_.id), id));

        em.createQuery(criteriaDelete).executeUpdate();

        String uuid = findById(id).getUuid();
        deleteDirectory(new File(ROOT + uuid));
    }


    private String saveImageLocally (byte[] image, String uuid, String name, boolean isThumbnail)
            throws IOException {

        String folder = isThumbnail ? "/thumbnail_image/" : "/full_image/";
        String path = ROOT + uuid + folder + name + ".png";

        new File(ROOT + uuid + folder).mkdirs();

        FileOutputStream fos = new FileOutputStream(path);
        fos.write(image);
        fos.close();

        return path;
    }


    void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }
}