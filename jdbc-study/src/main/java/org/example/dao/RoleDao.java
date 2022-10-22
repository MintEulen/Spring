package org.example.dao;

import org.example.dto.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.example.dao.RoleDaoSqls.*;
// RoleDaoSqls에 정의한 상수를 사용하기 위해 static import를 사용
// (RoleDaoSqls에 선언된 변수를 클래스 이름없이 사용할 수 있게 해준다. )


@Repository
public class RoleDao {
    private NamedParameterJdbcTemplate jdbc; // 가장 중요, 이름을 이용해서 바인딩 하거나, 결과 값을 가져올 때 사용한다.
    private SimpleJdbcInsert insertAction; //insert문을 사용하기 위해서 필요한 객체
    private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class);
    // BeanPropertyRowMapper는 DB의 컬럼명과 bean 객체의 속성 명이 일치할 때 자동으로 객체변환을 시켜준다.
    // Role.class를 기반으로 rowMapper 생성


    public RoleDao(DataSource dataSource) { //DBConfig에 Bean으로 등록했던 DataSource 부분이 파라미터로 전달된다.
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        // 받아들인 파라미터를 가지고 NamedParameterJdbcTemplate 객체를 생성한다.
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("role");
        // insertAction은 객체 생성 시 dataSource와 사용할 테이블을 지정해줘야 한다.
    }

    public List<Role> selectAll() {
        return jdbc.query(SELECT_ALL, Collections.<String, Object>emptyMap(), rowMapper);
    /* selectAll 메소드 정의, Role을 여러 건 가져오기 때문에 List로 작성, 첫 번째 파라미터는 실제 쿼리문(RoleDaoSqls에 정의한 상수)
    두 번째 파라미터는 비어있는 맵 객체(Collections.<String, Object>emptyMap())선언, sql에 바인딩 할 값이 있으면 바인딩 값을 전달하는 목적으로 사용,
    바인딩할 값이 없기 때문에 emptyMap을 넣어줌
    세 번째 파라미터는 rowMapper, select한 결과를 DTO에 저장하는 목적으로 사용
    NamedJdbcTemplate는 Map을 이용해서 값을 바인딩함
    */
    }
    public int insert (Role role) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(role);
        // role 객체를 맵 객체로 변환, 선언한 roleId값을 role_id로 맵 객체를 생성해준다.
        // SqlParameterSource는 Sql에 들어갈 parameter Map 객체를 처리하는 인터페이스
        return insertAction.execute(params); // 값이 알아서 저장
    }

    public int update(Role role) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(role);
        return jdbc.update(UPDATE_ALL, params);
        // 두 번째 파라미터는 RoleDaoSqls에 :뒤에 나오는 값들 채워줄 필요가 있는데, 그 값을 매핑시켜주는 값을 가진 객체다.
        // sqlParmeterSource가 role을 DBColumn 이름에 맞춰서 Map으로 바꿔주는 역할을 수행한다.
    }

    public int deleteById(Integer id){
        Map<String, ?> params = Collections.singletonMap("roleId", id); // 파라미터는 key, values
        // sqlParameterSource를 사용해도 되지만 delete는 값이 딱 하나만 들어와서 객체를 생성해 줄 필요가 없다.
        // Collections.singletonMap은 값이 여러 개 들어가지 않고 딱 1 건만 넣어서 사용할 때 위처럼 사용할 수 있다.
        return jdbc.update(DELETE_BY_ROLE_ID, params);
    }

    public Role selectById(Integer id) {
        try {
            Map<String, ?> params = Collections.singletonMap("roleId", id);
            return  jdbc.queryForObject(SELECT_BY_ROLE_ID, params, rowMapper);
            // 한 건 select는 queryForObject를 이용, 첫 번째 파라미터는 쿼리,
            // 두 번째 파라미터는 위 컬랙션에 넣어둔 roleid 값, 마지막 파라미터는
        }catch (EmptyResultDataAccessException e) { // 조건이 맞는 값이 없으면 예외 발생
            return null;
        }
    }

}
