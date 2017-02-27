	import net.beadsproject.beads.core.*;
	import net.beadsproject.beads.data.*;
	import net.beadsproject.beads.ugens.*;
	import javax.sound.sampled.AudioFormat;

public class Record {
	 AudioContext ac;
	 Gain g, g2;
	 
	 RecordToSample rts;
	 Sample targetSample;
	 boolean recording = false;
	 boolean istargetSampleEmpty = true;
	 private Glide rateValue;
	
	public Record() {
		ac = new AudioContext();
		
		UGen microphoneIn = ac.getAudioInput();
		
		try {
			AudioFormat af = new AudioFormat(44100.F, 16, 1, true, true);
			targetSample = new Sample(0 ,2 ,44100.0F );
			targetSample.clear();
			rts = new RecordToSample(ac ,targetSample ,RecordToSample.Mode.INFINITE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		rts.addInput(microphoneIn);
		
		rts.pause(true);
		g = new Gain(ac, 1, (float) 1);
		g2 = new Gain(ac, 1, 0.5f);
		ac.out.addInput(g2);
		ac.out.addInput(g);
		ac.start();
		
	}
	
	public void record() {
		//set recording to true
		recording = true;
		ac.out.addDependent(rts);
		targetSample.clear();
		//indicate that target sample contains something
		istargetSampleEmpty = false;
		//start recording
		rts.start();
	}
	
	public void pause() {
		//set recording to false
		recording = false;
		rts.pause(true);
	}
	
	public void playRecording() {
		//rateValue = new Glide(ac, 0.5f);
		SamplePlayer sp = new SamplePlayer(ac,  targetSample);
		//sp.setRate(rateValue);
		ac.out.removeDependent(rts);
		g2.addInput(sp);
		sp.setKillOnEnd(true);
		sp.start();
	}
}
