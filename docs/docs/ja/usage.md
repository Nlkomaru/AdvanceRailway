# 基本的な使い方

## 主な使い方

### 駅の追加

`ar station register <stationId> <name> [point]`を使用して駅を追加します <br>

- `id` : 駅のID
- `name` : 駅の名前
- `point` : 駅の座標

#### 例

`ar station register mr01 もりもと駅`を実行すると、プレイヤーの位置に駅が追加されます。<br>
`ar station register mr01 もりもと駅 0,64,0`を実行すると、座標0,64,0に駅が追加されます。<br>

### グループの作成

`ar gropu register <id> <name>`を使用してグループを作成します

- `id` : グループのID
- `name` : グループの名前

#### 例

`ar gropu register mr もりもと線`を実行すると、もりもと線というグループが作成されます。<br>

### 路線の追加

`ar railway register <railwayId> <group> <start> <direction> <end>`を使用して路線を追加します

- `id` : 路線のID
- `group` : 路線のグループ
- `start` : 路線の始点
- `direction` : 路線の方向
- `end` : 路線の終点

始点と終点は、与えられた座標から最も近い駅が選択されます。
異なる駅の場合は後述する方法で変更できます。

#### 例

`ar railway register mr-1 mr 0,64,1 25,64,2 100,87,10`を実行すると、もりもと駅から東フォレストピア駅への路線が追加されます。

## 補足

### 路線の編集

`ar railway set group <railwayId> <group>` 路線のグループを変更します <br>
`ar railway set from <railwayId> <fromStation>` 路線の始点駅を変更します <br>
`ar railway set to <railwayId> <toStation>` 路線の終点駅を変更します <br>

#### 例

`ar railway set group mr-1 mr`を実行すると、路線mr-1のグループがmrに変更されます。<br>
`ar railway unset group mr-1` を実行すると、路線mr-1のグループが削除されます。<br>
`ar railway set fromStation mr-1 mr02`を実行すると、路線mr-1の始点駅がmr02に変更されます。<br>
`ar railway set toStation mr-1 mr03`を実行すると、路線mr-1の終点駅がmr03に変更されます。<br>

### 路線の削除

`ar railway remove <railwayId>`

#### 例

`ar railway remove mr-1`を実行すると、路線mr-1が削除されます。

### 路線の更新

`ar railway update <railwayId> <start> <direction> <end>`

`ar railway command-export`を使用するとコマンドを補完することができるので活用してください



