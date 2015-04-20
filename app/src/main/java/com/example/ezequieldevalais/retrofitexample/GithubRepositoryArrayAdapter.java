package com.example.ezequieldevalais.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Repository;

import java.util.List;

/**
 * Created by EzequielDeValais on 4/17/15.
 */

public class GithubRepositoryArrayAdapter extends BaseAdapter {
    List<Repository> repositories;
    Context context;

    public GithubRepositoryArrayAdapter(List<Repository> listOfRepositories, Context aContext){
            repositories = listOfRepositories;
            context = aContext;
    }

    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public Object getItem(int position) {
        return repositories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater;
            inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.repository_list, parent,false);
        }

        TextView txtRepositoryName = (TextView)convertView.findViewById(R.id.txtRepositoryName);
        TextView txtRepositoryId = (TextView)convertView.findViewById(R.id.txtRepositoryId);

        Repository repository = repositories.get(position);

        txtRepositoryName.setText(repository.getName());
        txtRepositoryId.setText(repository.getId().toString());

        return convertView;

    }
}
