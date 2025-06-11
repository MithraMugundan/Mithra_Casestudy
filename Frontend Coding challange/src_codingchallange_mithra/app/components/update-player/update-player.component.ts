import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/model/players';
import { PlayersService } from 'src/app/services/players.service';

@Component({
  selector: 'app-update-player',
  templateUrl: './update-player.component.html',
  styleUrls: ['./update-player.component.css']
})
export class UpdatePlayerComponent implements OnInit {

  playerId: number = 0;
  player: Player = new Player(); // model with default values

  constructor(
    private playerService: PlayersService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.playerId = Number(this.route.snapshot.paramMap.get('id'));
    this.getPlayerById(this.playerId);
  }

  getPlayerById(id: number) {
    this.playerService.getPlayerById(id).subscribe(
      (res) => {
        this.player = res;
      },
      (err) => {
        console.error('Failed to load player:', err);
      }
    );
  }

  updatePlayer() {
    this.playerService.update(this.playerId, this.player).subscribe(
      (res) => {
        console.log('Updated Player:', res);
        alert('Player updated successfully!');
      },
      (err) => {
        console.error('Failed to update player:', err);
        alert('Failed to update player.');
      }
    );
  }
}
