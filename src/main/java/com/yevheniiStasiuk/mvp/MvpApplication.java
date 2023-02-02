package com.yevheniiStasiuk.mvp;

import com.yevheniiStasiuk.mvp.model.Player;
import com.yevheniiStasiuk.mvp.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class MvpApplication implements CommandLineRunner {
	private final MainService mainService;

	@Autowired
	public MvpApplication(MainService mainService) {
		this.mainService = mainService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MvpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String[] fileNames = {
				"src/test/resources/Game1.txt",
				"src/test/resources/Game2.txt"
		};
		final Player mvpPlayer = mainService.playGame(Arrays.stream(fileNames).toList());
		if (mvpPlayer != null) {
			System.out.println(mvpPlayer);
		}
	}

}
