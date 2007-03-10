package wilos.presentation.assistant.model;

import java.util.Locale;

public class WizardOptions {
	private Locale locale;
	private int refreshTime;
	
	public WizardOptions(Locale locale, int refreshTime) {
		this.locale = locale;
		this.refreshTime = refreshTime;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
}
