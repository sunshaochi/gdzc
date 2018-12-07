package com.example.print.app.new_print.module;

import java.io.Serializable;
import java.util.ArrayList;

public class MergeCellInfos implements Serializable {
    private ArrayList<CellPoint> mergeCellPointArr;
    private ArrayList<CellPoint> targetCellPointArr;

    public ArrayList<CellPoint> getMergeCellPointArr() {
        return mergeCellPointArr;
    }

    public void setMergeCellPointArr(ArrayList<CellPoint> mergeCellPointArr) {
        this.mergeCellPointArr = mergeCellPointArr;
    }

    public ArrayList<CellPoint> getTargetCellPointArr() {
        return targetCellPointArr;
    }

    public void setTargetCellPointArr(ArrayList<CellPoint> targetCellPointArr) {
        this.targetCellPointArr = targetCellPointArr;
    }
}
