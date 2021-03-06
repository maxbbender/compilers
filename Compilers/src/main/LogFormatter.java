package main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter {
	public String format(LogRecord rec) {
		StringBuilder log = new StringBuilder();
		log.append(rec.getLevel());
		log.append(": " + formatMessage(rec));
		log.append("\n");
		return log.toString();
	}
}
