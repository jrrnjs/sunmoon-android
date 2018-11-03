package com.kllama.sunmoon.ui.shuttle

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseAdapter
import com.kllama.sunmoon.core.platform.BaseViewHolder
import com.kllama.sunmoon.models.*
import kotlinx.android.synthetic.main.item_shuttle_train_weekday.view.*

class ShuttleAdapter : BaseAdapter<Shuttle>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Shuttle> =
            WeekdayViewHolder(parent.context, parent, R.layout.item_shuttle_train_weekday)

    override fun onBindViewHolder(holder: BaseViewHolder<Shuttle>, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ShuttleTrainWeekday -> 0
            is ShuttleTrainWeekend -> 1
            is ShuttleTerminalWeekday -> 3
            is ShuttleTerminalWeekend -> 4
            is ShuttleOnyang -> 5
        }
    }

    class WeekdayViewHolder(context: Context, parent: ViewGroup, @LayoutRes layoutRes: Int)
        : BaseViewHolder<Shuttle>(context, parent, layoutRes) {

        override fun onViewCreated(item: Shuttle) {
            if (item is ShuttleTrainWeekday) {
                itemView.run {
                    item_train_weekday_textview_no.text = item.no
                    item_train_weekday_textview_asan_campus_start.text = item.asanCampus
                    item_train_weekday_textview_asan_station.text = item.asanStation
                    item_train_weekday_textview_cheonan_station.text = item.chenanStation
                    item_train_weekday_textview_spa_vil.text = item.yongamTown
                    item_train_weekday_textview_asan_station2.text = item.asanStation2
                    item_train_weekday_textview_asan_campus_arrival.text = item.asanCampus2
                }
            }
        }
    }
}