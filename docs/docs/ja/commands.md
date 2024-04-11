# コマンド

- [x] は実装済み
- [ ] は未実装<br>
  `[]` 内はオプショナル引数です。
  `<>` 内は必須引数です。

# コマンドリスト

## 一般

- [x] `ar inspect` クリックした線路の終点を検出します。
- [x] `ar help` ヘルプを表示します。
- [x] `ar info` プラグインの情報を表示します。
- [x] `ar reload` マップ及び設定をリロードします。
- [ ] `ar export <type>` マップデータをエクスポートします。 type は `json` か `csv` です。

## 路線

- [x] `ar railway add <railwayId> <railwayName> <start> <direction> <end>` 路線を追加します。
- [x] `ar railway update <railwayId> <start> <direction> <end>` 路線の始点と終点を変更します。
- [ ] `ar railway remove <railwayId>` 路線を削除します。
- [ ] `ar railway set name <railwayId> <oldName> <newName>` 路線の名前を変更します。
- [ ] `ar railway set lineType <railwayId> <lineType>` 路線の色を変更します。
- [ ] `ar railway set group <railwayId> <groupId>` 路線のグループを変更します。
- [ ] `ar railway unset group <railwayId> <stationId>` 路線のグループを削除します。
- [ ] `ar railway set from-station <railwayId> <fromStation>` 路線の始点駅を変更します。
- [ ] `ar railway set to-station <railwayId> <toStation>` 路線の終点駅を変更します。
- [ ] `ar railway info <railwayId>` 路線の情報を表示します。
- [ ] `ar railway list` 路線の一覧を表示します。

## 駅

- [ ] `ar station add <stationId> <stationName> [point]` 駅を追加します。 pointがない場合はプレイヤーの位置に追加されます。
- [ ] `ar station remove <stationId>` 駅を削除します。
- [ ] `ar station rename <stationId> <oldName> <newName>` 駅の名前を変更します。
- [ ] `ar station set <stationId> <point>` 駅の座標を変更します。
- [ ] `ar station info <stationId>` 駅の情報を表示します。
- [ ] `ar station list` 駅の一覧を表示します。

## グループ

- [ ] `ar group add <groupId> <groupName>` グループを追加します。
- [ ] `ar group remove <groupId>` グループを削除します。
- [ ] `ar group rename <groupId> <oldName> <newName>` グループの名前を変更します。
- [ ] `ar group info <groupId>` グループの情報を表示します。
- [ ] `ar group list` グループの一覧を表示します。

路線名はかざしたとき
駅名は切り換えれるように
かかる時間
JY01