import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-project-objective',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-objective.component.html',
  styleUrl: './project-objective.component.scss',
})
export class ProjectObjectiveComponent {
  title: string = 'Objetivo do Projeto';
  generalObjective: string = `
    Facilitar a gestão de custos e a coordenação de decisões para eventos colaborativos, melhorando a experiência de organização e participação.
  `;
  specificObjectives: string[] = [
    'Facilitar a tomada de decisões em grupo para eventos.',
    'Prover transparência nos custos associados aos eventos.',
    'Centralizar todas as informações relevantes do evento em um só lugar.',
    'Reduzir atritos e conflitos financeiros entre os participantes.',
  ];
}
