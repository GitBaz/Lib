package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.activities.CommentList;
import ir.myandroidapp.library.backend.BackendComment;

/**
 * Created by kam.amir on 5/3/17.
 */

public class CardComment extends LinearLayout {

    TextView username;
    TextView comment;
    TextView say;
    LinearLayout reply;

    BackendComment backendComment;
    Core core;


    public CardComment(final Context ctx, Core cre, final BackendComment bc) {
        super(ctx);
        core = cre;
        backendComment = bc;

        LayoutInflater.from(ctx).inflate(R.layout.comment_view, this);

        username = (TextView) findViewById(R.id.comment_view_username);
        comment = (TextView) findViewById(R.id.comment_view_comment);
        say = (TextView) findViewById(R.id.comment_view_say);
        reply = (LinearLayout) findViewById(R.id.comment_view_reply);

        username.setTypeface(core.setTypeFace());
        comment.setTypeface(core.setTypeFace());

        say.setTypeface(core.setTypeFace());

        username.setText(bc.getUserName());
        comment.setText(bc.getComment());

        reply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                core.intentActivityPutExtra(CommentList.class,"commentId",bc.getId());
            }
        });

    }

    public void getReply(final ReplyResponse response){

        reply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(core.context, core, backendComment.getComment(),
                        "نظر خود را بنویسید ...", 150, new DialogInput.Response() {
                    @Override
                    public void resp(String result) {
                        response.response(result);
                    }
                });
            }
        });

    }

    public interface ReplyResponse{
        void response(String s);
    }


}