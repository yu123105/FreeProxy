package com.fish.proxy.dao;



import com.fish.proxy.bean.model.BaseModel;
import com.fish.proxy.bean.model.Page;
import com.fish.proxy.bean.model.PageRequest;
import com.fish.proxy.bean.model.Params;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IBaseDao<T extends BaseModel, PK extends Serializable> {
    Integer insert(final T domain);

    Integer update(final T domain);

    Integer deleteById(final PK id);

    Integer deleteByIds(final Collection<PK> ids);

    T findById(final PK id);

    T selectOne(final Params params);

    List<T> findByParams(final Params params);

    Long countByParams(final Params params);

    Page<T> findPageByParams(final Params params, final PageRequest pageRequest);
}
