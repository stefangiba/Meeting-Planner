package Teste;

import Proiect_ISP.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class Teste {
	@Test
	void VizualizareSedinteSiNotificari() {
		
		HashSet<SalaConferinte> sali = new HashSet<>();
		ArrayList<Persoana> persoane = new ArrayList<>();
		
		Manager manager1 = new Manager();
		Manager manager2 = new Manager();
		Manager manager3 = new Manager();
		
		SalaConferinte s1 = new SalaConferinte("A", 15);
		SalaConferinte s2 = new SalaConferinte("B", 10);
		SalaConferinte s3 = new SalaConferinte("C", 7);
		
		sali.add(s1);
		sali.add(s2);
		sali.add(s3);
		
		Angajat a1 = new Angajat("Istrate", "Ruxandra", "2990330297271", "sandrinais@yahoo.com", "dadada", manager1);
		Angajat a2 = new Angajat("Rudeanu", "Adrian", "1990330297271", "rudydudy@yahoo.com", "dadada", manager1);
		Angajat a3 = new Angajat("Vasilache", "Cosmin", "1990330297271", "cvasilache@gmail.com", "dadada", manager1);
		
		persoane.add(a1);
		persoane.add(a2);
		persoane.add(a3);
		
		ByteArrayInputStream in1 = new ByteArrayInputStream(("24/05/2020" + System.lineSeparator() + "12:00-13:30" + System.lineSeparator() + "C" + System.lineSeparator() + "Intalnirea 1").getBytes());
		Intalnire intalnire1 = Main.creareIntalnire(manager1,in1, sali, persoane);
		ByteArrayInputStream in2 = new ByteArrayInputStream(("24/05/2020" + System.lineSeparator() + "12:00-15:30" + System.lineSeparator() + "A" + System.lineSeparator() + "Intalnirea 2").getBytes());
		Intalnire intalnire2 = Main.creareIntalnire(manager1,in2, sali, persoane);
		ByteArrayInputStream in3 = new ByteArrayInputStream(("24/05/2020" + System.lineSeparator() + "12:00-13:30" + System.lineSeparator() + "B" + System.lineSeparator() + "Intalnirea 3").getBytes());
		Intalnire intalnire3 = Main.creareIntalnire(manager1,in3, sali, persoane);
		ByteArrayInputStream in4 = new ByteArrayInputStream(("24/05/2020" + System.lineSeparator() + "15:00-16:30" + System.lineSeparator() + "B" + System.lineSeparator() + "Intalnirea 3").getBytes());
		persoane.remove(a2);
		Intalnire intalnire4 = Main.creareIntalnire(manager1,in4, sali, persoane);
		ByteArrayInputStream in5 = new ByteArrayInputStream(("25/05/2020" + System.lineSeparator() + "15:00-16:30" + System.lineSeparator() + "B" + System.lineSeparator() + "Intalnirea 3").getBytes());
		persoane.remove(a1);
		Intalnire intalnire5 = Main.creareIntalnire(manager1,in5, sali, persoane);
		
		
		//TESTARE PENTRU INTALNIRI
		//Testare creare notificare: in total ar trebui sa fie 4 intalniri pentru angajatul a1
		assertEquals(4, a1.getNotificari().size());
		//Test pentru angajatul a2 care a fost scos din lista de participanti pentru intalnirea4 deci ar trebui sa aiba cu o intalnire mai putin decat angajatul a1
		assertEquals(3, a2.getNotificari().size());
		//Test pentru angajatul a3 care are o intalnire in plus fata de angajatul a1 pentru ca este singurul care a mai ramas in lista de participanti pentru intalnirea5
		assertEquals(5, a3.getNotificari().size());
		
		
		//TESTARE PENTRU NOTIFICARI
		//Testare creare notificare: in total ar trebui sa fie 4 notificari pentru angajatul a1 
		assertEquals(4, a1.getNotificari().size());
		//Test pentru angajatul a2 care a fost scos din lista de participanti pentru intalnirea4 deci ar trebui sa aiba cu o notificare mai putin decat angajatul a1
		assertEquals(3, a2.getNotificari().size());
		//Test pentru angajatul a3 care are o notificare in plus fata de angajatul a1 pentru ca este singurul care a mai ramas in lista de participanti pentru intalnirea5
		assertEquals(5, a3.getNotificari().size());
		//Test pentru angajatul a3 care dupa afisarea notificarilor nu ar trebui sa mai a vreo notificare deoarece se presupune ca au fost vizualizate de acesta
		a3.afiseazaNotificari();
		assertEquals(0, a3.getNotificari().size());
		//Testul pentru manager1 care nu ar trebui sa aiba nicio notificare pentru ca el a realizat toate intalnirile
		assertEquals(0, manager1.getNotificari().size());
		
		ByteArrayInputStream in6 = new ByteArrayInputStream(("26/05/2020" + System.lineSeparator() + "15:00-16:30" + System.lineSeparator() + "B" + System.lineSeparator() + "Intalnirea 3").getBytes());
		persoane.add(manager1);
		Intalnire intalnire6 = Main.creareIntalnire(manager2,in6, sali, persoane);
		//Test pentru manager1 care ar trebui sa aiba acum o notificare intrucat a fost convocat la o sedinta de catre manager2
		assertEquals(1, manager1.getNotificari().size());
	}
	
	
	

}