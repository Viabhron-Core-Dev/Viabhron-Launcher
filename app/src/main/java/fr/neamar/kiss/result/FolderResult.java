package fr.neamar.kiss.result;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        showFolderPopup(context, parent);
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

    private void showFolderPopup(Context context, RecordAdapter parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_folder, null);

        TextView folderNameView = dialogView.findViewById(R.id.folder_name);
        folderNameView.setText(folderName);
        folderNameView.setOnClickListener(v -> showEditNameDialog(context, parent));

        builder.setView(dialogView)
                .setTitle(R.string.folder_contents)
                .setPositiveButton(R.string.ok, null);

        builder.create().show();
    }

    private void showEditNameDialog(Context context, RecordAdapter parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_folder_name, null);

        EditText editText = dialogView.findViewById(R.id.edit_folder_name);
        editText.setText(folderName);

        builder.setView(dialogView)
                .setTitle(R.string.edit_folder_name)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    setFolderName(editText.getText().toString());
                    parent.notifyDataSetChanged();
                })
                .setNegativeButton(R.string.cancel, null);

        builder.create().show();
    }
}
