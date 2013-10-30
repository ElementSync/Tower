package com.droidplanner.dialogs.mission;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.droidplanner.R;
import com.droidplanner.drone.variables.mission.waypoints.LoiterTurns;
import com.droidplanner.widgets.SeekBarWithText.SeekBarWithText;
import com.droidplanner.widgets.SeekBarWithText.SeekBarWithText.OnTextSeekBarChangedListner;

public class DialogMissionLoiterN extends DialogMission implements
		OnTextSeekBarChangedListner, OnCheckedChangeListener {
	

	private SeekBarWithText altitudeSeekBar;
	private SeekBarWithText loiterTurnSeekBar;
	private SeekBarWithText loiterRadiusSeekBar;
	private CheckBox loiterCCW;
	private SeekBarWithText yawSeekBar;
	private LoiterTurns wp;

	@Override
	protected int getResource() {
		return R.layout.dialog_mission_loitern;
	}

	protected View buildView() {
		super.buildView();

		loiterCCW = (CheckBox) view.findViewById(R.string.loiter_ccw);
		if (wp.getRadius() < 0) {
			loiterCCW.setChecked(true);
		} else {
			loiterCCW.setChecked(false);
		}
		loiterCCW.setOnCheckedChangeListener(this);

		altitudeSeekBar = (SeekBarWithText) view
				.findViewById(R.id.altitudeView);
		altitudeSeekBar.setValue(wp.getAltitude().valueInMeters());
		altitudeSeekBar.setOnChangedListner(this);
		
		loiterTurnSeekBar = (SeekBarWithText) view
				.findViewById(R.id.loiterTurn);
		loiterTurnSeekBar.setOnChangedListner(this);
		loiterTurnSeekBar.setValue(wp.getTurns());

		loiterRadiusSeekBar = (SeekBarWithText) view
				.findViewById(R.id.loiterRadius);
		loiterRadiusSeekBar.setAbsValue(wp.getRadius());
		loiterRadiusSeekBar .setOnChangedListner(this);

		yawSeekBar = (SeekBarWithText) view
				.findViewById(R.id.waypointAngle);
		yawSeekBar.setValue(wp.getAngle());
		yawSeekBar.setOnChangedListner(this);

		return view;
	}


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		wp.setRadius(loiterRadiusSeekBar.getValue());
		if (loiterCCW.isChecked()) {
			wp.setRadius(wp.getRadius()*-1.0);
		}
    }
	
	
	@Override
	public void onSeekBarChanged() {
		wp.getAltitude().set(altitudeSeekBar.getValue());
		wp.setTurns((int)loiterTurnSeekBar.getValue());
		wp.setRadius(loiterRadiusSeekBar.getValue());
		if (loiterCCW.isChecked()) {
			wp.setRadius(wp.getRadius()*-1.0);
		}
		wp.setAngle(yawSeekBar.getValue());
	}

}
