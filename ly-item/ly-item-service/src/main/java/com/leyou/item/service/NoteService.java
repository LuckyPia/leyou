package com.leyou.item.service;

import com.leyou.item.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;
}
