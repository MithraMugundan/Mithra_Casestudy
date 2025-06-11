import { Component } from '@angular/core';
import { Player } from 'src/app/model/players';
import { PlayersService } from 'src/app/services/players.service';

@Component({
  selector: 'app-display-all',
  templateUrl: './display-all.component.html',
  styleUrls: ['./display-all.component.css']
})
export class DisplayallComponent {

  playerList: Player[] = [];

  constructor(private playerService: PlayersService) {}

  ngOnInit() {
    this.getAllPlayers();
  }

  getAllPlayers() {
    this.playerService.getAll().subscribe(
      (list) => {
        this.playerList = list;
        console.log(list);
      }
    );
  }

  deleteById(playerId: number) {
    this.playerService.delete(playerId).subscribe(
      (msg) => {
        console.log('Deleted ' + msg);
        this.getAllPlayers(); // Refresh the list after delete
      }
    );
  }
}
