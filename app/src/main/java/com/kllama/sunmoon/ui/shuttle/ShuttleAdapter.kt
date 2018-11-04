package com.kllama.sunmoon.ui.shuttle

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseAdapter
import com.kllama.sunmoon.core.platform.BaseViewHolder
import com.kllama.sunmoon.models.*
import kotlinx.android.synthetic.main.item_shuttle_onyang_weekday.view.*
import kotlinx.android.synthetic.main.item_shuttle_terminal_weekday.view.*
import kotlinx.android.synthetic.main.item_shuttle_terminal_weekend.view.*
import kotlinx.android.synthetic.main.item_shuttle_train_weekday.view.*
import kotlinx.android.synthetic.main.item_shuttle_train_weekend.view.*

class ShuttleAdapter : BaseAdapter<Shuttle>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Shuttle> {
        val type = ShuttleType.values()[viewType]
        return when (type) {
            ShuttleType.TRAIN_WEEKDAY -> TrainWeekdayViewHolder(parent.context, parent, R.layout.item_shuttle_train_weekday)
            ShuttleType.TRAIN_SATURDAY, ShuttleType.TRAIN_SUNDAY -> TrainWeekendViewHolder(parent.context, parent, R.layout.item_shuttle_train_weekend)
            ShuttleType.TERMINAL_WEEKDAY -> TerminalWeekdayViewHolder(parent.context, parent, R.layout.item_shuttle_terminal_weekday)
            ShuttleType.TERMINAL_SATURDAY, ShuttleType.TERMINAL_SUNDAY -> TerminalWeekendViewHolder(parent.context, parent, R.layout.item_shuttle_terminal_weekend)
            ShuttleType.ONYANG_WEEKDAY -> OnyangWeekdayViewHolder(parent.context, parent, R.layout.item_shuttle_onyang_weekday)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Shuttle>, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        //Weekend -> saturday, sunday
        return when (getItem(position)) {
            is ShuttleTrainWeekday -> 0
            is ShuttleTrainWeekend -> 1
            is ShuttleTerminalWeekday -> 3
            is ShuttleTerminalWeekend -> 4
            is ShuttleOnyang -> 6
        }
    }

    class TrainWeekdayViewHolder(context: Context, parent: ViewGroup, @LayoutRes layoutRes: Int)
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

    class TrainWeekendViewHolder(context: Context, parent: ViewGroup, @LayoutRes layoutRes: Int)
        : BaseViewHolder<Shuttle>(context, parent, layoutRes) {

        override fun onViewCreated(item: Shuttle) {
            if (item is ShuttleTrainWeekend) {
                itemView.run {
                    item_train_weekend_textview_no.text = item.no
                    item_train_weekend_textview_asan_campus_start.text = item.asanCampus
                    item_train_weekend_textview_asan_station.text = item.asanStation
                    item_train_weekend_textview_cheonan_station.text = item.chenanStation
                    item_train_weekend_textview_asan_station2.text = item.asanStation2
                    item_train_weekend_textview_asan_campus_arrival.text = item.asanCampus2
                }
            }
        }
    }

    class TerminalWeekdayViewHolder(context: Context, parent: ViewGroup, @LayoutRes layoutRes: Int)
        : BaseViewHolder<Shuttle>(context, parent, layoutRes) {

        override fun onViewCreated(item: Shuttle) {
            if (item is ShuttleTerminalWeekday) {
                itemView.run {
                    item_terminal_weekday_textview_no.text = item.no
                    item_terminal_weekday_textview_asan_campus_start.text = item.asanCampus
                    item_terminal_weekday_textview_cterminal.text = item.terminal
                    item_terminal_weekday_textview_hanjeon.text = item.hanjeon
                    item_terminal_weekday_textview_asan_campus_arrival.text = item.asanCampus2
                }
            }
        }
    }


    class TerminalWeekendViewHolder(context: Context, parent: ViewGroup, @LayoutRes layoutRes: Int)
        : BaseViewHolder<Shuttle>(context, parent, layoutRes) {

        override fun onViewCreated(item: Shuttle) {
            if (item is ShuttleTerminalWeekend) {
                itemView.run {
                    item_terminal_weekend_textview_no.text = item.no
                    item_terminal_weekend_textview_asan_campus_start.text = item.asanCampus
                    item_terminal_weekend_textview_cterminal.text = item.terminal
                    item_terminal_weekend_textview_asan_campus_arrival.text = item.asanCampus2
                }
            }
        }
    }

    class OnyangWeekdayViewHolder(context: Context, parent: ViewGroup, @LayoutRes layoutRes: Int)
        : BaseViewHolder<Shuttle>(context, parent, layoutRes) {

        override fun onViewCreated(item: Shuttle) {
            if (item is ShuttleOnyang) {
                itemView.run {
                    item_onyang_weekday_textview_no.text = item.no
                    item_onyang_weekday_textview_asan_campus_start.text = item.asanCampus
                    item_onyang_weekday_textview_baebang.text = item.baebang
                    item_onyang_weekday_textview_oterminal.text = item.terminal
                    item_onyang_weekday_textview_ostation.text = item.onyangStation
                    item_onyang_weekday_textview_asan_campus_arrival.text = item.asanCampus2
                }
            }
        }
    }
}