// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package Proiect_ISP;

import java.util.ArrayList;

public class Manager extends Persoana {
	private Departament departament;
	
	public Manager() {
		super();
		this.departament = Departament.AccountingFinance;
	}
	
	public Manager(String nume, String prenume, String cnp, String email, String parola, Departament departament) {
		super(nume, prenume, cnp, email, parola);
		this.departament = departament;
	}
	
	public Manager(String nume, String prenume, String cnp, String email, String parola, Departament departament,
			ArrayList<Notificare> listaNotificari, ArrayList<Intalnire> intalniri) {
		super(nume, prenume, cnp, email, parola, listaNotificari, intalniri);
		this.departament = departament;
	}

	public Intalnire creareIntalnire(String data, String startIntalnire, String stopIntalnire, ArrayList<Persoana> listaParticipanti,
			SalaConferinte sala, String mesaj) {
		String interval = startIntalnire + "-" + stopIntalnire;
		if(sala.verificareSala(data, interval)) {
			Intalnire intalnire = new Intalnire(data, startIntalnire, stopIntalnire, listaParticipanti, sala, mesaj);
			intalnire.setResponsabil(this);
			for(Persoana participant: listaParticipanti) {
				participant.adaugaIntalnire(intalnire);
			}
			this.adaugaIntalnire(intalnire);
			return intalnire;
		}
		return null;
	}
	
	public void anulareIntalnire(int index) {
		try {
			Intalnire intalnire = this.getIntalniri().get(index);
			ArrayList<Persoana> listaParticipanti = intalnire.getListaParticipanti();
			this.stergeIntalnire(intalnire);
			for (Persoana participant: listaParticipanti) {
				participant.stergeIntalnire(intalnire);
			}
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Nu exista aceasta intalnire");
		}
		
	}
};
