package kr.co.kmac.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "kr.co.kmac.pms.*")
@EnableScheduling
public class KmacPmsRenewalApplication {

	public static void main(String[] args) {
		SpringApplication.run(KmacPmsRenewalApplication.class, args);
	}

}
