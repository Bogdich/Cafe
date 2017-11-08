package com.bogdevich.cafe.entity.wrapper;

import java.util.Objects;

public class AnswerWrapper<T> {

    private T answer;

    public AnswerWrapper() {
    }

    public AnswerWrapper(T answer) {
        this.answer = answer;
    }

    public T getAnswer() {
        return answer;
    }

    public void setAnswer(T answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnswerWrapper)) return false;
        AnswerWrapper<?> that = (AnswerWrapper<?>) o;
        return Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnswerWrapper{");
        sb.append("answer=").append(answer);
        sb.append('}');
        return sb.toString();
    }
}
