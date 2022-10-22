package org.example.dao;

public class RoleDaoSqls {
    public static final String SELECT_ALL = "SELECT role_id, description FROM role order by role_id";
    // pulic static final해서 상수로 지정하고 전체 select 해오는 쿼리문
    public static final String UPDATE_ALL = "UPDATE role set description = :description WHERE role_id = :roleId";
    public static final String SELECT_BY_ROLE_ID = "SELECT role_id, description FROM role WHERE role_id = :roleId";
    public static final String DELETE_BY_ROLE_ID = "DELETE FROM role WHERE role_id = :roleId";
}
