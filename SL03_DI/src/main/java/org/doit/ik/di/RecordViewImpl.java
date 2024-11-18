package org.doit.ik.di;

import java.util.Scanner;

import lombok.Setter;

@Setter
public class RecordViewImpl implements RecordView{
	
	// 결합력이 높은 코딩 (안좋음)
	// private RecordImpl record = new RecordImpl();
		
	private RecordImpl record = null;
	
	public RecordViewImpl() {
		
	}
	
	public RecordViewImpl(RecordImpl record) {
		this.record = record;
	}

	@Override
	public void input() {
		try ( Scanner scanner = new Scanner(System.in)){
			System.out.println("> kor,eng,mat input ? ");
			int kor = scanner.nextInt();
			int eng = scanner.nextInt();
			int mat = scanner.nextInt();
			
			this.record.setKor(kor);
			this.record.setEng(eng);
			this.record.setMat(mat);
		} catch (Exception e) {
			e.getStackTrace();
		}// try
	}// input

	@Override
	public void output() {
		System.out.printf("> kor=%d, eng=%d, mat=%d, tot=%d, avg=%.2f\n",
							this.record.getKor(),
							this.record.getEng(),
							this.record.getMat(),
							this.record.total(),
							this.record.avg()
						 );
	}
	
}// class