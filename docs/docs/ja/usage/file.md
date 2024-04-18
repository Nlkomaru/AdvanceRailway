# ファイル編集による変更

## 使い方

`ar file export <dataType> <fileType>` でマップデータをエクスポートします。 dataTypeは`station`, `railway`, `group`
のいずれかです。 fileTypeは`json`, `csv`のいずれかです。
このコマンドを実行すると、`plugins/AdvancedRailway/` フォルダにエクスポートされたファイルが保存されます。
その後、エディターなどでファイルを編集し、`ar file import <dataType> <fileName>` でファイルをインポートします。
なおこの際、ファイルの形式は拡張子によって判断されます。

!!! warning
ファイル操作はデータの破損の原因となります。十分な注意を払って操作してください。
また、file importを使用した際、現在のデータは上書きされます。十分なバックアップを取ってから操作してください。
    