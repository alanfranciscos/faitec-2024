import {
  Component,
  Input,
  OnInit,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { Map, TileLayer, MapOptions, Marker } from 'leaflet';
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
    center: [-14.239209931938646, -50.261992558398134],
    zoom: 4,
    layers: [
      new TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
      }),
    ],
  };

  private addMarker(lat: number, lng: number): void {
    if (this.map) {
      const marker = new Marker([lat, lng], { draggable: true });
      marker.addTo(this.map).bindPopup('Arraste-me!').openPopup();

      marker.on('dragend', (event) => {
        const target = event.target;
        const position = target.getLatLng();
        console.log('Local:', position.lat, position.lng);
      });
    }
  }

  ngOnInit(): void {
    this.initMap();
    this.addMarker(-14.239209931938646, -50.261992558398134);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['width'] || changes['height']) {
      this.updateMapSize();
    }
  }

  private initMap(): void {
    this.map = new Map(this.mapContainerId, this.optionsMap);
    this.updateMapSize();
  }

  private updateMapSize(): void {
    if (this.map) {
      const mapContainer = document.getElementById(this.mapContainerId);
      if (mapContainer) {
        mapContainer.style.width = this.width + 'px';
        mapContainer.style.height = this.height + 'px';
        this.map.invalidateSize();
      }
    }
  }
}
