package com.yc.dao;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:17
 */

public interface StudentDao {

    public int add(String name);

    public void update(String name);

    public String find(String name);
}
