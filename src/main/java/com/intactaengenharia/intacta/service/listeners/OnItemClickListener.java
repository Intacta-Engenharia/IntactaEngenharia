package com.intactaengenharia.intacta.service.listeners;

public interface OnItemClickListener<T> {
    void onItemClick(T item);
    void onLongClick(String id);
}
