package com.server.vo;

import java.util.List;

/**
 * @author CYX
 * @create 2018-05-31-20:03
 */
public class SomeThingVO {

    private String id;
    private List<PeopleVO> peopleVO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PeopleVO> getPeopleVO() {
        return peopleVO;
    }

    public void setPeopleVO(List<PeopleVO> peopleVO) {
        this.peopleVO = peopleVO;
    }
}
