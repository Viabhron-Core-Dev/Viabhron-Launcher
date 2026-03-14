package fr.neamar.kiss.result;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fr.neamar.kiss.R;
import fr.neamar.kiss.adapter.RecordAdapter;
import fr.neamar.kiss.ui.ListPopup;

public class FolderResult extends Result<List<Result<?>>> {
    private String folderName;

    public FolderResult(String folderName, List<Result<?>> contents) {
        super(contents);
        this.folderName = folderName;
    }

    @Override
    public View display(@NonNull Context context, View convertView, @NonNull ViewGroup parent, FuzzyScore fuzzyScore) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.result_folder, null);
        }

        TextView nameView = convertView.findViewById(R.id.folder_name);
        nameView.setText(folderName);

        return convertView;
    }

    @Override
    public void launch(@NonNull Context context, View v, RecordAdapter parent) {
        // TODO: Open folder popup
    }

    @Override
    public ListPopup getPopupMenu(@NonNull Context context, RecordAdapter parent, View anchor) {
        return new ListPopup(context, parent, anchor, this);
    }

    @Override
    public long getUniqueId() {
        return folderName.hashCode();
    }

    @Override
    public String getSection() {
        return folderName.substring(0, 1).toUpperCase();
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<Result<?>> getContents() {
        return getData();
    }

    public void addApp(Result<?> app) {
        getData().add(app);
    }

    public void removeApp(Result<?> app) {
        getData().remove(app);
    }
}
