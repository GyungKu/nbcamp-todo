package com.sparta.nbcamptodo.repository;

import static com.sparta.nbcamptodo.entity.QTodo.*;
import static com.sparta.nbcamptodo.entity.QUser.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.entity.QTodo;
import com.sparta.nbcamptodo.entity.QUser;
import com.sparta.nbcamptodo.entity.Todo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class TodoQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Todo> searchTodoList(TodoCondition condition, Pageable pageable) {
        List<Todo> list = queryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user, user)
            .fetchJoin()
            .where(searchTitle(condition).or(searchContent(condition)))
            .orderBy(todo.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> count = queryFactory.select(todo.count())
            .from(todo)
            .where(searchTitle(condition).or(searchContent(condition)))
            .orderBy(todo.createdAt.desc());

        return PageableExecutionUtils.getPage(list, pageable, count::fetchOne);
    }

    private BooleanExpression searchTitle(TodoCondition condition) {
        if (StringUtils.hasText(condition.getTitle())) {
            return todo.title.contains(condition.getTitle());
        }
        return null;
    }

    private BooleanExpression searchContent(TodoCondition condition) {
        if (StringUtils.hasText(condition.getContent())) {
            return todo.content.contains(condition.getContent());
        }
        return null;
    }

}
