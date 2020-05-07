package Proiect_ISP;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static String email;
	private static String parola;
	private static String actiune;
	private static HashSet<Persoana> persoane = new HashSet<>();
	private static HashSet<SalaConferinte> sali = new HashSet<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SalaConferinte s1 = new SalaConferinte("A", 15);
		SalaConferinte s2 = new SalaConferinte("B", 10);
		SalaConferinte s3 = new SalaConferinte("C", 7);
		sali.add(s1);
		sali.add(s2);
		sali.add(s3);
		
		Manager m1 = new Manager("Giba", "Stefan", "1990125297244", "s", "shifu", Departament.ResearchDevelopment);
		
		Angajat a1 = new Angajat("Istrate", "Ruxandra", "2990330297271", "sandrinais@yahoo.com", "dadada", m1);
		Angajat a2 = new Angajat("Rudeanu", "Adrian", "1990330297271", "rudydudy@yahoo.com", "dadada", m1);
		Angajat a3 = new Angajat("Vasilache", "Cosmin", "1990330297271", "cvasilache@gmail.com", "dadada", m1);
		
		
		persoane.add(m1);
		persoane.add(a1);
		persoane.add(a2);
		persoane.add(a3);
		
		Persoana utilizatorCurent = null;
		
		while(true) {
			int var = 0;
			System.out.print("Continuati catre autentificare sau parasiti aplicatia? (login/exit) ");
			actiune = scanner.nextLine();
			if(actiune.toLowerCase().equals("exit")) {
				System.out.println("La revedere!");
				System.exit(0);
			} else if(actiune.toLowerCase().equals("login")){
				System.out.print("Introduceti email-ul: ");
				email = scanner.nextLine();
				System.out.print("Introduceti parola: ");
				parola = scanner.nextLine();
				for(Persoana persoana: persoane) {
					if(persoana.getEmail().equals(email) && persoana.getParola().equals(parola)) {
						utilizatorCurent = persoana;	
						System.out.println("Bun venit, " + utilizatorCurent.getPrenume() + "!");
						System.out.println("");
					}
				}
				
				afisareNotificari(utilizatorCurent);
				printOptionMenu(utilizatorCurent);
				while(utilizatorCurent != null) {
					System.out.println("AVETI " + utilizatorCurent.getNotificari().size() + " NOTIFICARI NOI.");
					System.out.print("Introduceti optiunea dorita: ");
					var = scanner.nextInt();
					scanner.nextLine();
					System.out.println("");
					switch (var) {
					case -1: {
						utilizatorCurent = null;
						break;
					}
					case 0: {
						printOptionMenu(utilizatorCurent);
						break;
					}
					case 1: {
						utilizatorCurent.afisareIntalniri();
						break;
					}
					case 2: {
						afisareNotificari(utilizatorCurent);
						break;
					}
					case 3: {
						Intalnire i1 = null;
						if(utilizatorCurent.getClass().getName().equals("Proiect_ISP.Manager")) {
							while(i1 == null) {
								i1 = creareIntalnire(utilizatorCurent,null, sali);
							}
							break;
						} else {
							System.out.println("Nu aveti drepturile necesare pentru a efectua aceasta operatie!");
							break;
						}
					}
					case 4: {
						if(utilizatorCurent.getClass().getName().equals("Proiect_ISP.Manager")) {
							anulareIntalnire(utilizatorCurent);
						} else {
							System.out.println("Nu aveti drepturile necesare pentru a efectua aceasta operatie!");
							break;
						}
					}
					}
				}
			} 
	}
		
	}
	
	
	public static Intalnire creareIntalnire(Persoana manager, ByteArrayInputStream in,HashSet<SalaConferinte> sali) {
		if(in != null) {
			scanner = new Scanner(in);
		}

		System.out.print("Introduceti data sedintei(dd/mm/yyyy): ");
		String data = scanner.nextLine();
		System.out.print("Introduceti intervalul orar: (hh:mm-hh:mm)");
		String interval = scanner.nextLine();
		String startIntalnire = interval.split("-")[0];
		String stopIntalnire = interval.split("-")[1];
		ArrayList<Persoana> participanti = new ArrayList<>();
		for (Persoana persoana: persoane) {
			if(persoana.getClass().getName().equals("Proiect_ISP.Angajat")) {
				if(((Angajat) persoana).getManager().equals(manager)) {
					participanti.add(persoana);
				}
			}
		}
		ArrayList<SalaConferinte> saliLibere = new ArrayList<>();
		System.out.print("Salile disponibile sunt: ");
		for(SalaConferinte sala: sali) {
			if(sala.verificareSala(data, interval)) {
				saliLibere.add(sala);
				System.out.print(sala.getNumeSala() + " ");
				sala.getOrar().get(data).remove(interval);

			}
			
		}
		if(saliLibere.isEmpty()) {
			System.out.println("Nu sunt sali libere la data si ora selecate.");
			System.out.println("Va rugam reintroduceti datele pentru creare sedinta.");
			return null;
		}
		System.out.println("");
		System.out.print("Introduceti sala dorita: ");
		String numeSala = scanner.nextLine();
		SalaConferinte sala = null;
		for(SalaConferinte s: sali) {
			if(s.getNumeSala().equals(numeSala) ) {
				sala = s;
			}
		}
		System.out.println("Introduceti un mesaj: ");
		String mesaj = scanner.nextLine();
		
		return ((Manager) manager).creareIntalnire(data, startIntalnire, stopIntalnire, participanti, sala, mesaj);
	}
	
	public static Intalnire creareIntalnire(Persoana manager, ByteArrayInputStream in,HashSet<SalaConferinte> sali, ArrayList<Persoana> participanti) {
		if(in != null) {
			scanner = new Scanner(in);
		}
		System.out.print("Introduceti data sedintei(dd/mm/yyyy): ");
		String data = scanner.nextLine();
		System.out.print("Introduceti intervalul orar: (hh:mm-hh:mm)");
		String interval = scanner.nextLine();
		String startIntalnire = interval.split("-")[0];
		String stopIntalnire = interval.split("-")[1];
		ArrayList<SalaConferinte> saliLibere = new ArrayList<>();
		System.out.print("Salile disponibile sunt: ");
		for(SalaConferinte sala: sali) {
			if(sala.verificareSala(data, interval)) {
				saliLibere.add(sala);
				System.out.print(sala.getNumeSala() + " ");
				sala.getOrar().get(data).remove(interval);
			}
			

			}

		
		if(saliLibere.isEmpty()) {
			System.out.println("Nu sunt sali libere la data si ora selecate.");
			System.out.println("Va rugam reintroduceti datele pentru crearea sedinta.");
			return null;
		}
		System.out.println("");
		System.out.print("Introduceti sala dorita: ");
		String numeSala = scanner.nextLine();
		SalaConferinte sala = null;
		for(SalaConferinte s: sali) {
			if(s.getNumeSala().equals(numeSala) ) {
				sala = s;
			}
		}
		System.out.println("Introduceti un mesaj: ");
		String mesaj = scanner.nextLine();
		
		return ((Manager) manager).creareIntalnire(data, startIntalnire, stopIntalnire, participanti, sala, mesaj);
	}
	
	public static void anulareIntalnire(Persoana manager) {
		manager.afisareIntalniri();
		System.out.print("Introduceti indexul intalnirii care trebuie sa fie anulata: ");
		int index = scanner.nextInt();
		((Manager) manager).anulareIntalnire(index - 1);
	}
	
	public static void afisareNotificari(Persoana persoana) {
		if(persoana.getNotificari().size() != 0) {
			System.out.println("Ai " + persoana.getNotificari().size() + " notificari noi: ");
			persoana.afiseazaNotificari();
			System.out.println("");
		} else { 
			System.out.println("Nu aveti notificari noi.");
			System.out.println("");
		}
	}
	
	public static void printOptionMenu(Persoana persoana) {
		System.out.println("Alegeti una dintre urmtoarele optiuni:");
		System.out.println("     -1 - pentru delogare");
		System.out.println("     0  - pentru afisarea meniului cu optiuni");
		System.out.println("     1  - pentru afisarea listei de intalniri");
		System.out.println("     2  - pentru afisarea listei de notificari");
		if(persoana.getClass().getName().equals("Proiect_ISP.Manager")) {
			System.out.println("     3 - creare intalnire");
			System.out.println("     4 - anulare intalnire");
		}
		System.out.println("");
	}

}
