package com.android.codelab.spacexcrew.diffutil;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.android.codelab.spacexcrew.model.SpaceX;
import com.android.codelab.spacexcrew.room.CrewEntity;

import java.util.List;

public class DiffUtilCallBacksForMainActivity extends DiffUtil.Callback {

    private final List<SpaceX> crewOldList;
    private final List<SpaceX> crewNewList;

    public DiffUtilCallBacksForMainActivity(List<SpaceX> crewOldList, List<SpaceX> crewNewList) {
        this.crewOldList = crewOldList;
        this.crewNewList = crewNewList;
    }

    @Override
    public int getOldListSize() {
        return crewOldList != null ? crewOldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return crewNewList != null ? crewNewList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return crewOldList.get(oldItemPosition).getId() == crewNewList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final SpaceX oldCrew = crewOldList.get(oldItemPosition);
        final SpaceX newCrew = crewNewList.get(newItemPosition);
        return oldCrew.getName().equals(newCrew.getName());


    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator

        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
