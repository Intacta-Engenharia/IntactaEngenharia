package com.intactaengenharia.intacta.Interfaces;

import com.intactaengenharia.intacta.Beans.Diary;
import java.util.ArrayList;

public interface DiaryModelListener {

    interface OnCompleteDiariesListener {
        void callbackDiaries(ArrayList<Diary> diarylist);
    }

    /*interface OnCompleteDiaryListener {
        void callbackDiary(Diary diary);
    }*/




}
