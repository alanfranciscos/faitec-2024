import {
  Component,
  Input,
  OnInit,
  OnChanges,
  SimpleChanges,
  Output,
  EventEmitter,
} from '@angular/core';
import { Map, TileLayer, MapOptions, Marker } from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { Coordinate } from '../../domain/model/event/eventLocalization.model';

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
  @Input() coordinates = { lat: -14.239209931938646, lng: -50.261992558398134 };
  @Input() dragable: boolean = false;
  @Input() popUpInfo: string = 'Arraste-me!';
  @Output() coordinatesChange = new EventEmitter<Coordinate>();

  private mapContainerId = 'map';

  private optionsMap: MapOptions = {
    maxBoundsViscosity: 1.0,
    minZoom: 2,
    center: [this.coordinates.lat, this.coordinates.lng],
    zoom: 4,
    layers: [
      new TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
      }),
    ],
  };

  private addMarker(lat: number, lng: number): void {
    if (this.map) {
      const marker = new Marker([lat, lng], { draggable: this.dragable });
      marker.addTo(this.map).bindPopup(this.popUpInfo).openPopup();

      marker.on('dragend', (event) => {
        const target = event.target;
        const position = target.getLatLng();
        this.coordinates.lat = position.lat;
        this.coordinates.lng = position.lng;

        this.coordinatesChange.emit(this.coordinates);
      });
    }
  }

  ngOnInit(): void {
    this.initMap();
    this.addMarker(this.coordinates.lat, this.coordinates.lng);
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
