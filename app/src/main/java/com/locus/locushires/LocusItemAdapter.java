package com.locus.locushires;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.locus.locushires.databinding.LocusItemBinding;
import com.locus.locushires.databinding.LocusItemBindingComment;
import com.locus.locushires.databinding.LocusItemBindingOptions;
import com.locus.locushires.databinding.LocusItemBindingPhoto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocusItemAdapter extends RecyclerView.Adapter<LocusItemAdapter.LocusItemViewHolder> {

    private LayoutInflater layoutInflater;
    private List<LocusItemViewModel> data;
    private ItemClickListener clickListener;
    private ItemTouchListener touchListener;
    private Context context;

    public interface ItemClickListener {
        public void onClick(View view, LocusItemBinding locusItemBinding, int position);
        public void onClick(View view, LocusItemBindingPhoto locusItemBindingPhoto, int position);
        public void onClick(View view, LocusItemBindingComment locusItemBindingComment, int position);
        public void onClick(View view, LocusItemBindingOptions locusItemBindingOptions, int position);

    }

    public interface ItemTouchListener {
        public boolean onTouch(View v, MotionEvent event);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void setTouchListener(ItemTouchListener itemTouchListener) {
        this.touchListener = itemTouchListener;
    }

    public LocusItemAdapter(List<LocusItemViewModel> locusItemViewModel, Context context) {
        this.data = locusItemViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public LocusItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        if (viewType == 0){
            LocusItemBindingPhoto locusItemBinding = LocusItemBindingPhoto.inflate(layoutInflater, parent, false);
            return new LocusItemViewHolder(locusItemBinding);
        } else if (viewType == 1){
            LocusItemBindingComment locusItemBinding = LocusItemBindingComment.inflate(layoutInflater, parent, false);
            return new LocusItemViewHolder(locusItemBinding);
        } else if (viewType == 2){
            LocusItemBindingOptions locusItemBinding = LocusItemBindingOptions.inflate(layoutInflater, parent, false);
            return new LocusItemViewHolder(locusItemBinding);
        } else {
            LocusItemBinding locusItemBinding = LocusItemBinding.inflate(layoutInflater, parent, false);
            return new LocusItemViewHolder(locusItemBinding);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final LocusItemViewHolder holder, final int position) {
        holder.bind(data.get(position));
        if (holder.viewType == 0){
            if (data.get(position).item_photo == null || data.get(position).item_photo.isEmpty()){
                holder.locusItemBindingPhoto.itemPhoto.setImageDrawable(null);
                holder.locusItemBindingPhoto.itemPhoto.setOnTouchListener(null);
                holder.locusItemBindingPhoto.itemPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onClick(holder.locusItemBindingPhoto.itemPhoto, holder.locusItemBindingPhoto, position);
                    }
                });
            } else {
                holder.locusItemBindingPhoto.itemPhoto.setOnClickListener(null);
                holder.locusItemBindingPhoto.itemPhoto.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return touchListener.onTouch(view,motionEvent);
                    }
                });
            }
            holder.locusItemBindingPhoto.btnSubmitPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.locusItemBindingPhoto.itemPhoto.getDrawable() != null)
                        Toast.makeText(context, context.getString(R.string.thanks_successfully_submitted), Toast.LENGTH_SHORT).show();
                }
            });
            holder.locusItemBindingPhoto.btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.locusItemBindingPhoto.itemPhoto.setImageDrawable(null);
                    holder.locusItemBindingPhoto.itemPhoto.setOnTouchListener(null);
                    holder.locusItemBindingPhoto.itemPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickListener.onClick(holder.locusItemBindingPhoto.itemPhoto, holder.locusItemBindingPhoto, position);
                        }
                    });                }
            });
        }
        if (holder.viewType == 1){
            holder.locusItemBindingComment.commentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        holder.locusItemBindingComment.itemCommentx.setVisibility(View.VISIBLE);
                        holder.locusItemBindingComment.btnSubmitComment.setVisibility(View.VISIBLE);
                    } else {
                        holder.locusItemBindingComment.itemCommentx.setVisibility(View.INVISIBLE);
                        holder.locusItemBindingComment.btnSubmitComment.setVisibility(View.INVISIBLE);
                    }
                }
            });
            holder.locusItemBindingComment.btnSubmitComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.locusItemBindingComment.itemCommentx.getText() != null)
                        Toast.makeText(context, context.getString(R.string.thanks_successfully_submitted), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (holder.viewType == 2){
            if(data.get(position).item_options!=null && !data.get(position).item_options.isEmpty()){
                String[] options = data.get(position).item_options.split("\t");
                for (int i = 0; i < options.length; i++){
                    if (i == 0) {
                        holder.locusItemBindingOptions.choiceOne.setText(options[i]);
                        holder.locusItemBindingOptions.choiceOne.setVisibility(View.VISIBLE);
                    }
                    if (i == 1) {
                        holder.locusItemBindingOptions.choiceTwo.setText(options[i]);
                        holder.locusItemBindingOptions.choiceTwo.setVisibility(View.VISIBLE);
                    }
                    if (i == 2) {
                        holder.locusItemBindingOptions.choiceThree.setText(options[i]);
                        holder.locusItemBindingOptions.choiceThree.setVisibility(View.VISIBLE);
                    }
                    if (i == 3) {
                        holder.locusItemBindingOptions.choiceFour.setText(options[i]);
                        holder.locusItemBindingOptions.choiceFour.setVisibility(View.VISIBLE);
                    }
                    if (i == 4) {
                        holder.locusItemBindingOptions.choiceFive.setText(options[i]);
                        holder.locusItemBindingOptions.choiceFive.setVisibility(View.VISIBLE);
                    }
                    if (i == 5) {
                        holder.locusItemBindingOptions.choiceSix.setText(options[i]);
                        holder.locusItemBindingOptions.choiceSix.setVisibility(View.VISIBLE);
                    }
                }
            }
            holder.locusItemBindingOptions.btnSubmitOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.locusItemBindingOptions.radioGroupOptions.getCheckedRadioButtonId() != -1)
                        Toast.makeText(context, context.getString(R.string.thanks_successfully_submitted), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        int viewtype = -1;
        LocusItemViewModel locusItemViewModel = data.get(position);
        switch (locusItemViewModel.item_type){
            case MainActivity.PHOTO_TYPE:
                viewtype = 0;
                break;
            case MainActivity.COMMENT_TYPE:
                viewtype = 1;
                break;
            case MainActivity.OPTIONS_TYPE:
                viewtype = 2;
                break;
        }
        return viewtype;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LocusItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LocusItemBinding locusItemBinding;
        private LocusItemBindingPhoto locusItemBindingPhoto;
        private LocusItemBindingComment locusItemBindingComment;
        private LocusItemBindingOptions locusItemBindingOptions;
        int viewType;

        public LocusItemViewHolder(@NonNull LocusItemBindingPhoto itemViewBinding) {
            super(itemViewBinding.getRoot());
            locusItemBindingPhoto = itemViewBinding;
            viewType = 0;
        }

        public LocusItemViewHolder(@NonNull LocusItemBindingComment itemViewBinding) {
            super(itemViewBinding.getRoot());
            locusItemBindingComment = itemViewBinding;
            viewType = 1;

        }

        public LocusItemViewHolder(@NonNull LocusItemBindingOptions itemViewBinding) {
            super(itemViewBinding.getRoot());
            locusItemBindingOptions = itemViewBinding;
            viewType = 2;
        }

        public LocusItemViewHolder(@NonNull LocusItemBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            locusItemBinding = itemViewBinding;
            viewType = -1;
        }

        public void bind(LocusItemViewModel locusItemViewModel){
            if (locusItemBinding!= null)
                this.locusItemBinding.setViewModel(locusItemViewModel);
            if (locusItemBindingPhoto!= null)
                this.locusItemBindingPhoto.setViewModel(locusItemViewModel);
            if (locusItemBindingComment!= null)
                this.locusItemBindingComment.setViewModel(locusItemViewModel);
            if (locusItemBindingOptions!= null)
                this.locusItemBindingOptions.setViewModel(locusItemViewModel);
        }

        public LocusItemBinding getLocusItemBinding(){
            return locusItemBinding;
        }
        public LocusItemBindingPhoto getLocusItemBindingPhoto(){
            return locusItemBindingPhoto;
        }
        public LocusItemBindingComment getLocusItemBindingComment(){
            return locusItemBindingComment;
        }
        public LocusItemBindingOptions getLocusItemBindingOptions(){
            return locusItemBindingOptions;
        }

        @Override
        public void onClick(View view) {
            if (viewType == 1){
                clickListener.onClick(view, locusItemBindingPhoto, getPosition());
            }
        }
    }

}
