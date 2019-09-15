package com.example.weibo.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import com.example.weibo.R

class WBCommentRelatedUserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wbcomment_related_user, container, false)
    }

    @Target(AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(COMMENT_TYPE_BY_ME, COMMENT_TYPE_TO_ME)
    annotation class CommentType

    companion object {
        const val COMMENT_TYPE_BY_ME:Int = 5
        const val COMMENT_TYPE_TO_ME:Int = 6

        fun newInstance(@CommentType type:Int) =
            WBCommentRelatedUserFragment().apply {
                arguments = Bundle().apply {
                    putInt("commentType",type)
                }
            }
    }
}
