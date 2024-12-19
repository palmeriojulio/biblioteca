import { Component, Input, OnInit } from '@angular/core';
import { Emprestimo } from '../../models/emprestimo';

@Component({
  selector: 'app-emprestimo-info',
  templateUrl: './emprestimo-info.component.html',
  styleUrls: ['./emprestimo-info.component.scss']
})
export class EmprestimoInfoComponent implements OnInit {

  @Input() emprestimo!: Emprestimo;

  constructor() { }

  ngOnInit(): void {
  }

}
