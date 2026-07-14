package online_registration;
import java.time.LocalDate;

public class Term {
	private int termId;
	private String termName;
	private LocalDate startDate;
	private LocalDate endDate;

	public Term(String termName, LocalDate startDate, LocalDate endDate) {
		this.termName = termName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Term(int termId, String termName, LocalDate startDate, LocalDate endDate) {
		this.termId = termId;
		this.termName = termName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Getters and Setters
	public int getTermId() { return termId; }
	public void setTermId(int termId) { this.termId = termId; }

	public String getTermName() { return termName; }
	public void setTermName(String termName) { this.termName = termName; }

	public LocalDate getStartDate() { return startDate; }
	public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

	public LocalDate getEndDate() { return endDate; }
	public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
