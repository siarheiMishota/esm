package com.epam.esm;


import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoConfiguration.class);
        GiftCertificateDao tagDaoImpl = context.getBean("giftCertificateDao", GiftCertificateDao.class);

//        System.out.println(tagDaoImpl.findAll().stream().findFirst());
        GiftCertificate giftCertificate = new GiftCertificate(10, "name 10", "description 10",
                BigDecimal.valueOf(10), LocalDateTime.now(), LocalDateTime.now(), 10);
        tagDaoImpl.add(giftCertificate);

        System.out.println(giftCertificate);
        System.out.println("___________________________________________________________________________\n update\n");
        giftCertificate.setName("New name 1001010101");
        tagDaoImpl.update(giftCertificate);
        System.out.println(giftCertificate);
        System.out.println("___________________________________________________________________________\n update end\n");

        tagDaoImpl.delete(giftCertificate.getId());

//        TagDao tagDaoImpl = context.getBean("tagDao", TagDao.class);
//        Tag tag = new Tag();
//        tag.setId(3);
//        tag.setName("abra-kadabra");
//        tagDaoImpl.delete(3);

//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS hh");
//        df.setTimeZone(tz);
//        try {
//            String nowAsISO = df.format(LocalDateTime.now());
//            String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS hh"));
//            System.out.println(format);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }
}
