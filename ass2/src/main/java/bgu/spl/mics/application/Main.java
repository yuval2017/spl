package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		Diary diary = Diary.getInstance();

		List<Integer> l1 = new LinkedList<>();
		List<Integer> l2 = new LinkedList<>();
		List<Integer> l3 = new LinkedList<>();
		l1.add(3);
		l1.add(4);
		l2.add(3);
		l2.add(4);
		l3.add(1);
		l3.add(2);
		Attack[] attacks = new Attack[]{new Attack(l1, 1000), new Attack(l2, 1000),new Attack(l3,1000)};
		Ewoks ewoks = Ewoks.getInstance();

		Thread leia = new Thread(new LeiaMicroservice(attacks, 4));
		Thread r2d2 = new Thread(new R2D2Microservice(2000));
		Thread lando = new Thread(new LandoMicroservice(2000));
		Thread hanSolo = new Thread(new HanSoloMicroservice());
		Thread c3po = new Thread(new C3POMicroservice());

		leia.start();
		r2d2.start();
		lando.start();
		hanSolo.start();
		c3po.start();

		try {
			leia.join();
			r2d2.join();
			lando.join();
			hanSolo.join();
			c3po.join();
		}catch (InterruptedException e){}



		System.out.println(diary.getTotalAttacks() + " total attacks");
		System.out.println(diary.getC3POFinish() + " c3 finish to attack ");
		System.out.println(diary.getHanSoloFinish() + " ha finish to attack");
		System.out.println((diary.getR2D2Deactivate() + " r2d2 deactivate shield"));
		System.out.println("\n _____________________ \n");

		System.out.println(diary.getC3POTerminate() + " c3");
		System.out.println(diary.getHanSoloTerminate() + " han");
		System.out.println(diary.getR2D2Terminate() + " r2d2");
		System.out.println(diary.getLandoTerminate() + " lan");
		System.out.println(diary.getLeiaTerminate() + " leia");



	}
}
