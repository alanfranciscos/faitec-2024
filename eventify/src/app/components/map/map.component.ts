import {
  Component,
  Input,
  OnInit,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { Map, TileLayer, MapOptions } from 'leaflet';
import 'leaflet/dist/leaflet.css';

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [],
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit, OnChanges {
  private map: Map | undefined;
  @Input() width: string = '600';
  @Input() height: string = '420';
  private mapContainerId = 'map'; // ID do contêiner do mapa

  private optionsMap: MapOptions = {
    maxBoundsViscosity: 1.0,
    minZoom: 2,
    maxZoom: 10,
    center: [-14.239209931938646, -50.261992558398134],
    zoom: 4,
    layers: [
      new TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
      }),
    ],
  };

  ngOnInit(): void {
    this.initMap();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['width'] || changes['height']) {
      this.updateMapSize();
    }
  }

  private initMap(): void {
    this.map = new Map(this.mapContainerId, this.optionsMap);
    this.updateMapSize(); // Inicializa o tamanho do mapa com as propriedades iniciais
  }

  private updateMapSize(): void {
    if (this.map) {
      const mapContainer = document.getElementById(this.mapContainerId);
      if (mapContainer) {
        mapContainer.style.width = this.width + 'px';
        mapContainer.style.height = this.height + 'px';
        this.map.invalidateSize(); // Invalida o tamanho para ajustar a nova dimensão
      }
    }
  }
}
