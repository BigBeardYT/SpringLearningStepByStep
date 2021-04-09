package com.yc.dao;

import org.springframework.stereotype.Repository;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:17
 */
@Repository
public interface StudentDao {

    public int add(String name);

    public void update(String name);
}
