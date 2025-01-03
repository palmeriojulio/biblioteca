import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  cards = [
    { title: 'Livros', value: '786', icon: 'book'},
    { title: 'Leitores', value: '150', icon: 'person'},
    { title: 'Emprestimos', value: '15', icon: 'book'},
    { title: 'Entregas de Hoje', value: '8', icon: 'today'},
    { title: 'Em Atraso', value: '3', icon: 'schedule'}
  ];

  constructor() { }

  ngOnInit(): void {

    Chart.register(...registerables);

    const ctx = (document.getElementById('booksChart') as HTMLCanvasElement).getContext('2d');
    const doughnutChart = (document.getElementById('doughnutChart') as HTMLCanvasElement).getContext('2d');

    if (ctx) { // Verifica se ctx não é null
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['O Código da Inteligência', 'Pai Rico Pai Pobre', 'Como Fazer Amigos...', 'Macunaíma', 'O Senhor dos Aneis'],
          datasets: [{
            label: 'Livros mais emprestados',
            data: [30, 25, 20, 10, 5],
            backgroundColor: [
              'rgba(255, 205, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(201, 203, 207, 0.2)'
            ],
            borderColor: [
              'rgb(255, 205, 86)',
              'rgb(75, 192, 192)',
              'rgb(54, 162, 235)',
              'rgb(153, 102, 255)',
              'rgb(201, 203, 207)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          scales: {
            x: {
              beginAtZero: true
            },
            y: {
              beginAtZero: true
            }
          }
        }
      });
    }

    if (doughnutChart) {
      new Chart(doughnutChart, {
        type: 'doughnut',
        data: {
          labels: ['Livros com atraso'],
          datasets: [{
            label: 'Livros em atraso',
            data: [30, 25, 20, 10, 5],
            backgroundColor: [
              'rgba(255, 200, 70, 0.76)',
              'rgba(75, 192, 192, 0.75)',
              'rgba(54, 163, 235, 0.83)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(201, 203, 207, 0.2)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
        }
      });
    }
  }
}
