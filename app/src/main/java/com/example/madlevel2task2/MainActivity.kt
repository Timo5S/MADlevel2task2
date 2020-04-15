package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions: MutableList<Question> = ArrayList()
    private val questionAdapter = QuestionAdapter(questions as ArrayList<Question>)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questions.add(Question("A 'val' and 'var' are the same", false))
        questions.add(Question("Mobile Application Development grants 12 ECTS", false))
        questions.add(Question("A unit in Kotlin corresponds to a void in Java", false))
        questions.add(Question("In kotlin 'when' replaces the 'switch' operator in Java", true))

        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter
        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }
    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (((direction == ItemTouchHelper.RIGHT) and (questions[position].correctOrFalse)) ||
                    ((direction == ItemTouchHelper.LEFT) and (!questions[position].correctOrFalse))){
                    questions.removeAt(position)
                    Snackbar.make(clLayout, "Answer is correct", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(clLayout, "Answer is not correct", Snackbar.LENGTH_SHORT).show()
                }
                questionAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

}
