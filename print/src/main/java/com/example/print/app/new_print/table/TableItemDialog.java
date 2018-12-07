//package com.example.print.app.new_print.table;
//
//import android.content.Context;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.example.print.app.R;
//import com.example.print.app.new_print.base.BaseDialog;
//import com.project.skn.x.dialog.DialogFactory;
//
//import static com.project.skn.x.util.KeyBoardUtils.INSTANCE;
//
//public class TableItemDialog extends BaseDialog<TableItemView> {
//
//    public TableItemDialog(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected int getPopupLayout() {
//        return R.layout.dialog_table_menu;
//    }
//
//    /**
//     * 初始化行
//     */
//    private void initLine(TableItemView view) {
//        LinearLayout llAllLine = holder.getView(R.id.ll_all_line);
//        llAllLine.removeAllViews();
//        for (int i = 0; i < view.getLineCount(); i++) {
//            View itemView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_table_item, null, false);
//            TextView tvItemName = itemView.findViewById(R.id.tv_item_name);
//            tvItemName.setText("第" + (i + 1) + "行高度");
//            ImageView ivItemReduce = itemView.findViewById(R.id.iv_item_reduce);
//            TextView tvItem = itemView.findViewById(R.id.tv_item);
//            ImageView ivItemAdd = itemView.findViewById(R.id.iv_item_add);
//            llAllLine.addView(itemView);
//            llAllLine.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * 初始化列
//     */
//    private void initColumn(TableItemView view) {
//        LinearLayout llAllColumn = holder.getView(R.id.ll_all_column);
//        llAllColumn.removeAllViews();
//        for (int i = 0; i < view.getColumnCount(); i++) {
//            View itemView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_table_item, null, false);
//            TextView tvItemName = itemView.findViewById(R.id.tv_item_name);
//            tvItemName.setText("第" + (i + 1) + "列宽度");
//            ImageView ivItemReduce = itemView.findViewById(R.id.iv_item_reduce);
//            TextView tvItem = itemView.findViewById(R.id.tv_item);
//            ImageView ivItemAdd = itemView.findViewById(R.id.iv_item_add);
//            llAllColumn.addView(itemView);
//            llAllColumn.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * 格式布局
//     */
//    private void initFormatLayout(TableItemView view) {
//        //行
//        TextView tvLine = holder.getView(R.id.tv_line);
//        tvLine.setText(String.valueOf(view.getLineCount()));
//        initLine(view);
//        ImageView ivLineReduce = holder.getView(R.id.iv_line_reduce);
//        ivLineReduce.setOnClickListener(v -> {
//            view.setLineCount(view.getLineCount() - 1);
//            tvLine.setText(String.valueOf(view.getLineCount()));
//            initLine(view);
//        });
//        ImageView ivLineAdd = holder.getView(R.id.iv_line_add);
//        ivLineAdd.setOnClickListener(v -> {
//            view.setLineCount(view.getLineCount() + 1);
//            tvLine.setText(String.valueOf(view.getLineCount()));
//            initLine(view);
//        });
//        //列
//        TextView tvColumn = holder.getView(R.id.tv_column);
//        tvColumn.setText(String.valueOf(view.getColumnCount()));
//        initColumn(view);
//        ImageView ivColumnReduce = holder.getView(R.id.iv_column_reduce);
//        ivColumnReduce.setOnClickListener(v -> {
//            view.setColumnCount(view.getColumnCount() - 1);
//            tvColumn.setText(String.valueOf(view.getColumnCount()));
//            initColumn(view);
//        });
//        ImageView ivColumnAdd = holder.getView(R.id.iv_column_add);
//        ivColumnAdd.setOnClickListener(v -> {
//            view.setColumnCount(view.getColumnCount() + 1);
//            tvColumn.setText(String.valueOf(view.getColumnCount()));
//            initColumn(view);
//        });
//        //外框线宽
//        TextView tvOutStrokeWidth = holder.getView(R.id.tv_out_stroke_width);
//        tvOutStrokeWidth.setText(String.valueOf(view.getOutStrokeWidth()));
//        ImageView ivOutStrokeReduce = holder.getView(R.id.iv_out_stroke_width_reduce);
//        ivOutStrokeReduce.setOnClickListener(v -> {
//            view.setOutStrokeWidth(view.getOutStrokeWidth() - 1);
//            tvOutStrokeWidth.setText(String.valueOf(view.getOutStrokeWidth()));
//        });
//        ImageView ivOutStrokeAdd = holder.getView(R.id.iv_out_stroke_width_add);
//        ivOutStrokeAdd.setOnClickListener(v -> {
//            view.setOutStrokeWidth(view.getOutStrokeWidth() + 1);
//            tvOutStrokeWidth.setText(String.valueOf(view.getOutStrokeWidth()));
//        });
//
//        TextView tvInStrokeWidth = holder.getView(R.id.tv_in_stroke_width);
//        tvInStrokeWidth.setText(String.valueOf(view.getInStrokeWidth()));
//        ImageView ivnStrokeWidthReduce = holder.getView(R.id.iv_in_stroke_width_reduce);
//        ivnStrokeWidthReduce.setOnClickListener(v -> {
//            view.setInStrokeWidth(view.getInStrokeWidth() - 1);
//            tvInStrokeWidth.setText(String.valueOf(view.getInStrokeWidth()));
//        });
//        ImageView ivnStrokeWidthAdd = holder.getView(R.id.iv_in_stroke_width_add);
//        ivnStrokeWidthAdd.setOnClickListener(v -> {
//            view.setInStrokeWidth(view.getInStrokeWidth() + 1);
//            tvInStrokeWidth.setText(String.valueOf(view.getInStrokeWidth()));
//        });
//    }
//
//
//    @Override
//    public DialogFactory.Holder create(TableItemView view) {
//        //dialog只会初始化一次 并且和view生命周期绑定
//        //格式布局
//        RadioButton rbFormat = holder.getView(R.id.rb_format);
//        ScrollView llFormat = holder.getView(R.id.ll_format);
//        //键盘布局
//        RadioButton rbKeyboard = holder.getView(R.id.rb_keyboard);
//        ScrollView llKeyboard = holder.getView(R.id.ll_keyboard);
//        EditText etInput = holder.getView(R.id.et_input);
//        etInput.setText(view.getText());
//        //字体布局
//        RadioButton rbFont = holder.getView(R.id.rb_font);
//        ScrollView llFont = holder.getView(R.id.ll_font);
//        //导入
//        RadioButton rbImport = holder.getView(R.id.rb_import);
//        initFormatLayout(view);
//        //事件
//        rbFormat.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                INSTANCE.closeKeyboard(etInput);
//                llFormat.setVisibility(View.VISIBLE);
//                llKeyboard.setVisibility(View.GONE);
//                llFont.setVisibility(View.GONE);
//            }
//        });
//        rbKeyboard.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                llFormat.setVisibility(View.GONE);
//                llKeyboard.setVisibility(View.VISIBLE);
//                llFont.setVisibility(View.GONE);
//            }
//        });
//        rbFont.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                INSTANCE.closeKeyboard(etInput);
//                llFormat.setVisibility(View.GONE);
//                llKeyboard.setVisibility(View.GONE);
//                llFont.setVisibility(View.VISIBLE);
//            }
//        });
//        rbImport.setOnClickListener(v -> {
//            //
//        });
//        etInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                view.setText(etInput.getText().toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        return super.create(view);
//    }
//
//
//}
