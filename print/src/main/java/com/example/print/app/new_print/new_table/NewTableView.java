package com.example.print.app.new_print.new_table;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import com.example.print.app.new_print.ExcelChangeManager;
import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.base.IBackgroundLayer;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.module.CellPoint;
import com.example.print.app.new_print.module.MergeCellInfos;
import com.example.print.app.new_print.text.ITextItem;
import com.example.print.app.util.TranslateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewTableView extends BaseItemView implements ExcelChangeManager.IExcelDataChangeListener, IBackgroundLayer.FirstLayer, ITextItem {
    private GridView gridView;

    public static NewTableView create(Context context) {
        NewTableView newTableView = new NewTableView(context);
        newTableView.setSelected(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        newTableView.setLayoutParams(layoutParams);
        return newTableView;
    }


    public NewTableView(Context context) {
        super(context);
        gridView = new GridView(getContext(), 3, 3);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        gridView.setLayoutParams(layoutParams);
        addView(gridView);
        //按钮拖动回调
        setOnMoveListener(new OnMoveListener() {
            @Override
            public void onMove(int offsetX, int offsetY) {
                if (offsetX != 0) {
                    int allColumn = 0;
                    for (int i = 0; i < getGridView().getColumns().size(); i++) {
                        if (getGridView().getColumns().get(i) < 20 && offsetX < 0) return;
                        allColumn += getGridView().getColumns().get(i);
                    }
                    if (offsetX + allColumn <= 0) {
                        return;
                    }
                    for (int i = 0; i < getGridView().getColumns().size(); i++) {
                        getGridView().getColumns().set(i, getGridView().getColumns().get(i) + offsetX * getGridView().getColumns().get(i) / allColumn);
                    }
                } else if (offsetY != 0) {
                    int allLine = 0;
                    for (int i = 0; i < getGridView().getLines().size(); i++) {
                        if (getGridView().getLines().get(i) < 20 && offsetY < 0) return;
                        allLine += getGridView().getLines().get(i);
                    }
                    if (offsetY + allLine <= 0) {
                        return;
                    }

                    for (int i = 0; i < getGridView().getLines().size(); i++) {
                        getGridView().getLines().set(i, getGridView().getLines().get(i) + offsetY * getGridView().getLines().get(i) / allLine);
                    }
                }
                getGridView().flashLayout();
            }
        });
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (!selected) {
            getGridView().clearSelect();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void delete() {

    }

    public GridView getGridView() {
        return gridView;
    }

    @Override
    public BaseItemModule toBean() {
        BaseItemModule itemModule = super.toBean();
        itemModule.setLineWidth(TranslateUtils.getPxByLocalPx(getGridView().getLineWidth()));
        ArrayList<Float> columns=new ArrayList<>();
        ArrayList<Float> lines=new ArrayList<>();
        for (int i = 0; i < getGridView().getColumns().size(); i++) {
            columns.add(TranslateUtils.getPxByLocalPx(getGridView().getColumns().get(i)));
        }

        for (int i = 0; i < getGridView().getLines().size(); i++) {
            lines.add(TranslateUtils.getPxByLocalPx(getGridView().getLines().get(i)));
        }
        itemModule.setFormColumnWidths(columns);
        itemModule.setFormRowHeights(lines);
        itemModule.setTagType(15);
        ArrayList<ArrayList<BaseItemModule>> formItems = new ArrayList<>();
        ArrayList<MergeCellInfos> mergeCellInfos = new ArrayList<>();
        for (int i = 0; i < getGridView().getMerge().size(); i++) {
            ArrayList<CellPoint> mergeCellPointArr = new ArrayList<>();
            ArrayList<CellPoint> targetCellPointArr = new ArrayList<>();
            CellPoint start = new CellPoint();
            CellPoint end = new CellPoint();
            start.cellRow = getGridView().getMerge().get(i).getLine() - 1;
            start.cellColumn = getGridView().getMerge().get(i).getColumn() - 1;
            end.cellRow = getGridView().getMerge().get(i).getEndLine() - 1;
            end.cellColumn = getGridView().getMerge().get(i).getEndColumn() - 1;
            targetCellPointArr.add(start);
            targetCellPointArr.add(end);
            MergeCellInfos m = new MergeCellInfos();
            m.setMergeCellPointArr(mergeCellPointArr);
            m.setTargetCellPointArr(targetCellPointArr);
            mergeCellInfos.add(m);
            for (int j = 0; j < getGridView().getTableModules().size(); j++) {
                if (start.cellRow <= getGridView().getTableModules().get(j).getLine() - 1 && end.cellRow >= getGridView().getTableModules().get(j).getLine() - 1
                        && start.cellColumn <= getGridView().getTableModules().get(j).getColumn() - 1 && end.cellColumn >= getGridView().getTableModules().get(j).getColumn() - 1) {
                    CellPoint child = new CellPoint();
                    child.cellRow = getGridView().getTableModules().get(j).getLine() - 1;
                    child.cellColumn = getGridView().getTableModules().get(j).getColumn() - 1;
                    mergeCellPointArr.add(child);
                }
            }
        }
        itemModule.setMergeCellInfos(mergeCellInfos);
        for (int i = 0; i < getGridView().getLines().size(); i++) {
            ArrayList<BaseItemModule> baseItemModules = new ArrayList<>();
            for (int j = 0; j < getGridView().getTableModules().size(); j++) {
                if (getGridView().getTableModules().get(j).getLine() == i + 1) {
                    BaseItemModule baseItemModule = new BaseItemModule();
                    if (getGridView().getTableModules().get(j).getExcelData().size() > 0) {
                        baseItemModule.setString(getGridView().getTableModules().get(j).getExcelData().get(0));
                    } else {
                        baseItemModule.setString(getGridView().getTableModules().get(j).getText().toString());
                    }
                    baseItemModule.setTextSize(TranslateUtils.getServiceTextSize(
                            TranslateUtils.getPositionFromLocal(getGridView().getTableModules().get(j).getTextSize())));
                    baseItemModule.setTextFont(getGridView().getTableModules().get(j).textFont);
                    baseItemModule.setLineSpacing(getGridView().getTableModules().get(j).getLineSpacingExtra());
                    baseItemModule.setTagType(15);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        baseItemModule.setFontSpacing(getGridView().getTableModules().get(j).getLetterSpacing());
                    }
                    StringBuilder excelString = new StringBuilder();
                    for (int z = 0; z < getGridView().getTableModules().get(j).getExcelData().size(); z++) {
                        excelString.append(getGridView().getTableModules().get(j).getExcelData().get(z));
                        if (z < getGridView().getTableModules().get(j).getExcelData().size() - 1) {
                            excelString.append(":excel:");
                        }
                    }
                    baseItemModule.setExcelString(excelString.toString());
                    switch (getGridView().getTableModules().get(j).getGravity()) {
                        case Gravity.START | Gravity.CENTER_VERTICAL:
                            baseItemModule.setTextAlignment(0);
                            break;
                        case Gravity.CENTER:
                            baseItemModule.setTextAlignment(1);
                            break;
                        case Gravity.END | Gravity.CENTER_VERTICAL:
                            baseItemModule.setTextAlignment(2);
                            break;
                    }
                    baseItemModule.setFakeBoldText(getGridView().getTableModules().get(j).getPaint().isFakeBoldText());
                    baseItemModule.setUnderlineText(getGridView().getTableModules().get(j).getPaint().isUnderlineText());
                    baseItemModule.setTextSkewX(getGridView().getTableModules().get(j).getPaint().getTextSkewX() != 0);
                    baseItemModules.add(baseItemModule);
                }
            }
            formItems.add(baseItemModules);
        }
        itemModule.setFormItems(formItems);
        return itemModule;
    }

    @Override
    public void amplification() {
    }

    @Override
    public void narrow() {
    }

    @Override
    public void rota() {
    }

    @Override
    public BaseItemView copy() {
        return null;
    }

    @Override
    public NewTableView fromBean(BaseItemModule itemModule) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) (TranslateUtils.getPxByServicePx(itemModule.getY()));
        layoutParams.leftMargin = (int) (TranslateUtils.getPxByServicePx(itemModule.getX()));
        setLayoutParams(layoutParams);
        ArrayList<Integer> columns=new ArrayList<>();
        ArrayList<Integer> lines=new ArrayList<>();
        for (int i = 0; i < itemModule.getFormColumnWidths().size(); i++) {
            columns.add((int) TranslateUtils.getPxByServicePx(itemModule.getFormColumnWidths().get(i)));
        }

        for (int i = 0; i < itemModule.getFormRowHeights().size(); i++) {
            lines.add((int) TranslateUtils.getPxByServicePx(itemModule.getFormRowHeights().get(i)));
        }
        getGridView().setColumns(columns);
        getGridView().setLines(lines);
        getGridView().getTableModules().clear();
        getGridView().setLineWidth((int) (TranslateUtils.getPxByServicePx(itemModule.getLineWidth())));
        for (int i = 0; i < itemModule.getFormItems().size(); i++) {
            for (int j = 0; j < itemModule.getFormItems().get(i).size(); j++) {
                SelectTextView tableModule = getGridView().createTextView(itemModule.getFormItems().get(i).size() * i + j, 0, 0);
                if (itemModule.getExcelString() != null && itemModule.getFormItems().get(i).get(j).getExcelString().contains(":excel:")) {
                    tableModule.setExcelData(Arrays.asList(itemModule.getFormItems().get(i).get(j).getExcelString().split(":excel:")));
                }
                tableModule.setTextLineSpacing(itemModule.getFormItems().get(i).get(j).getLineSpacing());
                tableModule.setTextLetterSpacing(itemModule.getFormItems().get(i).get(j).getFontSpacing());
                tableModule.setTextBold(itemModule.getFormItems().get(i).get(j).isFakeBoldText());
                tableModule.setTextUnderline(itemModule.getFormItems().get(i).get(j).isUnderlineText());
                tableModule.setTextSkewX(itemModule.getFormItems().get(i).get(j).isTextSkewX() ? -0.5f : 0f);
                tableModule.textFont = itemModule.getFormItems().get(i).get(j).getTextFont();
                tableModule.setTextStyle(itemModule.getFormItems().get(i).get(j).getTextFont());
                tableModule.setText(itemModule.getFormItems().get(i).get(j).getString());
                tableModule.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        TranslateUtils.getPositionFromService(itemModule.getFormItems().get(i).get(j).getTextSize()) == -1 ?
                                TranslateUtils.getLocationTextSize(2) :
                                TranslateUtils.getLocationTextSize(
                                        TranslateUtils.getPositionFromService(itemModule.getFormItems().get(i).get(j).getTextSize())));
                switch (itemModule.getFormItems().get(i).get(j).getTextAlignment()) {
                    case 0:
                        tableModule.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                        break;
                    case 1:
                        tableModule.setGravity(Gravity.CENTER);
                        break;
                    case 2:
                        tableModule.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                        break;
                }
            }
        }
        if (itemModule.getMergeCellInfos() != null) {
            for (int i = 0; i < itemModule.getMergeCellInfos().size(); i++) {
                CellPoint minCellPoint = new CellPoint();
                CellPoint maxCellPoint = new CellPoint();
                if (itemModule.getMergeCellInfos().get(i).getTargetCellPointArr().size() < 2) continue;
                minCellPoint.cellRow = itemModule.getMergeCellInfos().get(i).getTargetCellPointArr().get(0).cellRow + 1;
                minCellPoint.cellColumn = itemModule.getMergeCellInfos().get(i).getTargetCellPointArr().get(0).cellColumn + 1;
                maxCellPoint.cellRow = itemModule.getMergeCellInfos().get(i).getTargetCellPointArr().get(1).cellRow + 1;
                maxCellPoint.cellColumn = itemModule.getMergeCellInfos().get(i).getTargetCellPointArr().get(1).cellColumn + 1;
                getGridView().createMergeTextView(minCellPoint, maxCellPoint);
                getGridView().hideCell(minCellPoint, maxCellPoint);
            }
        }
        getGridView().flashLayout();

        return this;
    }

    @Override
    public void onUpdate(int position) {

    }

    @Override
    public void setExcelData(List<String> excelData) {
        if (getGridView().getSelectTextView() == null) return;
        getGridView().getSelectTextView().setExcelData(excelData);
    }

    @Override
    public List<String> getExcelData() {
        List<String> excelData = new ArrayList<>();
        for (int i = 0; i < getGridView().getTableModules().size(); i++) {
            if (getGridView().getTableModules().get(i).hasExcelData() && excelData.size() < getGridView().getTableModules().get(i).getExcelData().size()) {
                excelData = getGridView().getTableModules().get(i).getExcelData();
            }
        }
        return excelData;
    }

    @Override
    public boolean hasExcelData() {
        return false;
    }

    @Override
    public void setText(String text) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTexts(text);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTexts(text);
                }
            }
        }
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void setTextStyle(String textFont) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextStyle(textFont);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextStyle(textFont);
                }
            }
        }
    }

    @Override
    public String getTextStyle() {
        return null;
    }

    @Override
    public void setTextSize(float size) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextSize(size);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextSize(size);
                }
            }
        }
    }

    @Override
    public float getTextSize() {
        return 0;
    }

    @Override
    public void setTextGravity(int gravity) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setGravity(gravity);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setGravity(gravity);
                }
            }
        }
    }

    @Override
    public int getTextGravity() {
        return 0;
    }

    @Override
    public void setTextLetterSpacing(float space) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextLetterSpacing(space);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextLetterSpacing(space);
                }
            }
        }
    }

    @Override
    public float getTextLetterSpacing() {
        return 0;
    }

    @Override
    public void setTextLineSpacing(float space) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextLineSpacing(space);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextLineSpacing(space);
                }
            }
        }
    }

    @Override
    public float getTextLineSpacing() {
        return 0;
    }

    @Override
    public void setTextBold(boolean flag) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextBold(flag);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextBold(flag);
                }
            }
        }
    }

    @Override
    public boolean getTextBold() {
        return false;
    }

    @Override
    public void setTextUnderline(boolean flag) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextUnderline(flag);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextUnderline(flag);
                }
            }
        }
    }

    @Override
    public boolean getTextUnderline() {
        return false;
    }

    @Override
    public void setTextSkewX(float f) {
        if (getGridView().isSingle()) {
            getGridView().getSelectTextView().setTextSkewX(f);
        } else {
            for (int i = 0; i < getGridView().getChildCount(); i++) {
                if (getGridView().getChildAt(i) instanceof SelectTextView && getGridView().getChildAt(i).isSelected()) {
                    ((SelectTextView) getGridView().getChildAt(i)).setTextSkewX(f);
                }
            }
        }
    }

    @Override
    public float getTextSkewX() {
        return 0;
    }
}
