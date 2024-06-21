import { Component, ElementRef } from '@angular/core';

import * as Plotly from 'plotly.js-dist-min';
import { PlotlyModule } from 'angular-plotly.js';
import { Month } from '../../domain/enums/monthsOfYear';

PlotlyModule.plotlyjs = Plotly;
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [PlotlyModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  screenProperties = {};
  constructor(private el: ElementRef) {}

  getMonthName(monthNumber: number): string {
    if (monthNumber < 1 || monthNumber > 12) {
      throw new Error('Invalid month number');
    }

    return Month[monthNumber];
  }

  getMonthForData(dataXAxis: number[]): string[] {
    return dataXAxis.map((monthNumber) => this.getMonthName(monthNumber));
  }

  private dataXAxis = this.getMonthForData([1, 2, 3]);
  private dataYAxis = [2, 6, 3];

  graph: {
    data: Plotly.Data[];
    layout: Partial<Plotly.Layout>;
    config: Partial<Plotly.Config>;
  } = {
    data: [
      {
        x: this.dataXAxis,
        y: this.dataYAxis,
        name: 'Events',
        type: 'scatter',
        mode: 'lines',
        fill: 'tozeroy',
        marker: { color: '#00B69B' },
      },
    ],
    layout: {
      width: this.el.nativeElement.offsetWidth,
      height: this.el.nativeElement.offsetHeight,
      showlegend: true,
      legend: { x: 0, y: -0.2 },
      yaxis: { title: 'Number of events' },
      xaxis: { title: 'Month' },
    },
    config: { responsive: true },
  };
}
