package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DaoConfigurationTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DaoConfigurationTest.class)
class UserDaoImplTest {

//    @Autowired
//    private UserDao userDao;
//
//    @Test
//    void findAll() {
//        List<User> all = userDao.findAll();
//        assertEquals(50, all.size());
//    }
//
//    @Test
//    void findAllWithParameters() {
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put(StringParameters.PATTERN_LIMIT, "30");
//        parameterMap.put(StringParameters.PATTERN_OFFSET, "10");
//        List<User> all = userDao.findAll(parameterMap);
//        assertEquals(30, all.size());
//    }
//
//    @Test
//    void findAllWithParametersBigOffset() {
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put(StringParameters.PATTERN_LIMIT, "30");
//        parameterMap.put(StringParameters.PATTERN_OFFSET, "100");
//        List<User> all = userDao.findAll(parameterMap);
//        assertEquals(List.of(), all);
//    }
//
//    @Test
//    void findAllWithParametersIncorrectLimit() {
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put(StringParameters.PATTERN_LIMIT, "32a");
//        parameterMap.put(StringParameters.PATTERN_OFFSET, "10");
//        assertThrows(BadSqlGrammarException.class, () -> userDao.findAll(parameterMap));
//    }
//
//    @Test
//    void findAllWithParametersIncorrectOffset() {
//        Map<String, String> parameterMap = new HashMap<>();
//        parameterMap.put(StringParameters.PATTERN_LIMIT, "32");
//        parameterMap.put(StringParameters.PATTERN_OFFSET, "aa2 ");
//        assertThrows(BadSqlGrammarException.class, () -> userDao.findAll(parameterMap));
//    }
//
//    @Test
//    void findById() {
//        User expected = new User();
//        expected.setId(50);
//        expected.setName("Jennee");
//        expected.setEmail("jmottram1d@un.org");
//        expected.setPassword("7faab5f29ab9712326938b3267de8f5c764d3e097cf84d937ace6b29bed0016f");
//
//        Order order = new Order();
//        order.setId(50);
//        order.setCost(BigDecimal.valueOf(50));
//
//        assertEquals(expected, userDao.findById(50).get());
//    }
//
//    @Test
//    void findByIdWithNegativeId() {
//        assertEquals(Optional.empty(), userDao.findById(-1));
//    }
//
//    @Test
//    void findByIdWithNotExist() {
//        assertEquals(Optional.empty(), userDao.findById(5456));
//    }
//
////    @Test
////    void add() {
////        User expected = new User();
////        expected.setId(50);
////        expected.setName("Jennee");
////        expected.setEmail("jmottram1d@un.org");
////        expected.setPassword("7faab5f29ab9712326938b3267de8f5c764d3e097cf84d937ace6b29bed0016f");
////        userDao.add(expected);
////    }
}