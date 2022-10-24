package org.example.service.impl;

import org.example.dao.GuestbookDao;
import org.example.dao.LogDao;
import org.example.dto.Guestbook;
import org.example.dto.Log;
import org.example.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GuestbookServiceImpl implements GuestbookService {
    @Autowired
    GuestbookDao guestbookDao;

    @Autowired
    LogDao logDao;

    @Override
    @Transactional
    public List<Guestbook> getGuestbooks(Integer start) {
        List<Guestbook> list = guestbookDao.selectAll(start, GuestbookService.LIMIT);
        return list;
        // 위 메소드 실행 시 guestbook 목록을 가져온다.
        // transactional이 없으면 읽기만 하는 메소드
        // Transactional은 내부적으로 readOnly 형태로 connection을 사용한다.
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteGuestbook(Long id, String ip) {
        int deleteCount = guestbookDao.deleteById(id);
        Log log = new Log();
        log.setIp(ip);
        log.setMethod("delete");
        log.setRegdate(new Date());
        logDao.insert(log);
        return deleteCount;
        // delete하는 메소드, 삭제하는 사용자들 ip를 받아와서 log테이블에 남기는 메소드
    }

    @Override
    @Transactional(readOnly = false)
    public Guestbook addGuestbook(Guestbook guestbook, String ip) {
        guestbook.setRegdate(new Date());
        Long id = guestbookDao.insert(guestbook);
        guestbook.setId(id);

        // 데이터 한 건 넣는 메소드,
        // guestbook이 입력되면서 자동으로 만들어진 id값을 얻어와서
        // guestbook에 id 값을 채워준다

//    if(1 == 1)
//       throw new RuntimeException("test exception");

        Log log = new Log();
        log.setIp(ip);
        log.setMethod("insert");
        log.setRegdate(new Date());
        logDao.insert(log);

        return guestbook;
    }

    @Override
    public int getCount() {
        return guestbookDao.selectCount();
        // 페이징 처리를 위한 메소드
    }
}