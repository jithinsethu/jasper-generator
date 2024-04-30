package io.jithset.jasperconverter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class JasperConverterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JasperConverterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (args.length != 1) {
			System.out.println("Usage: java -jar jrxml-to-jasper.jar <path_to_jrxml_file>");
			return;
		}

		String jrxmlFilePath = args[0];
		File jrxmlFile = new File(jrxmlFilePath);

		if (!jrxmlFile.exists() || !jrxmlFile.isFile()) {
			System.out.println("Invalid JRXML file path.");
			return;
		}

		try {
			String jasperFilePath = compileJrxmlToJasper(jrxmlFile);
			System.out.println("Jasper file generated: " + jasperFilePath);
		} catch (JRException e) {
			System.out.println("Error compiling JRXML to Jasper: " + e.getMessage());
		}
	}

	private String compileJrxmlToJasper(File jrxmlFile) throws JRException {
		String jasperFileName = jrxmlFile.getName().replaceAll(".jrxml", ".jasper");
		File jasperFile = new File(jrxmlFile.getParent(), jasperFileName);

		JasperCompileManager.compileReportToFile(jrxmlFile.getAbsolutePath(), jasperFile.getAbsolutePath());

		return jasperFile.getAbsolutePath();
	}
}
