# コマンド

- [x] は実装済み
- [ ] は未実装<br>
  `[]` 内はオプショナル引数です。
  `<>` 内は必須引数です。
  `arg...` は複数の引数を受け取ります。

# コマンドリスト

## 一般

- [x] `ar inspect` クリックした線路の終点を検出します。
- [x] `ar targetBlock` 見ているブロックの座標を表示します。
- [x] `ar help` ヘルプを表示します。
- [x] `ar info` プラグインの情報を表示します。
- [x] `ar reload` マップ及び設定をリロードします。
- [ ] `ar export <type>` マップデータをエクスポートします。 type は `json` か `csv` です。

## 路線

- [x] `ar railway add <railwayId> <railwayName> <start> <direction> <end>` 路線を追加します。
- [x] `ar railway update <railwayId> <start> <direction> <end>` 路線の始点と終点を変更します。 道中の路線が変化した場合もこちらを使用してください。
- [x] `ar railway set nam <railwayId>` 路線を削除します。
- [x] `ar railway set lineType <railwayId> <lineType>` 路線の色を変更します。
- [x] `ar railway set group <railwayId> <groupId>` 路線のグループを変更します。
- [x] `ar railway unset group <railwayId> <stationId>` 路線のグループを削除します。
- [x] `ar railway set from-station <railwayId> <fromStation>` 路線の始点駅を変更します。
- [x] `ar railway set to-station <railwayId> <toStation>` 路線の終点駅を変更します。
- [x] `ar railway info <railwayId>` 路線の情報を表示します。
- [x] `ar railway list` 路線の一覧を表示します。

## 駅

- [x] `ar station add <stationId> <stationName> [point]` 駅を追加します。 pointがない場合はプレイヤーの位置に追加されます。
- [x] `ar station remove <stationId>` 駅を削除します。
- [x] `ar station set name <stationId> <newName>` 駅の名前を変更します。
- [x] `ar station set location <stationId> [point]` 駅の座標を変更します。
- [x] `ar station set numbering <stationId> <newNumbering>` 駅の番号を変更します。
- [x] `ar station info <stationId>` 駅の情報を表示します。
- [x] `ar station list` 駅の一覧を表示します。

## グループ

- [x] `ar group add <groupId> <groupName>` グループを追加します。
- [x] `ar group remove <groupId>` グループを削除します。
- [x] `ar group set name <groupId> <oldName> <newName>` グループの名前を変更します。
- [x] `ar group set color <groupId> <r> <g> <b>` グループの色を変更します。
- [x] `ar group join <groupId> <stationId...> ` グループに駅を追加します。
- [x] `ar group info <groupId>` グループの情報を表示します。
- [x] `ar group list` グループの一覧を表示します。