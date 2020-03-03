<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Author>lastlySly</Author>
  <LastAuthor>lastlySly</LastAuthor>
  <Created>2015-06-05T18:19:34Z</Created>
  <LastSaved>2020-02-28T04:37:46Z</LastSaved>
  <Version>16.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>12645</WindowHeight>
  <WindowWidth>22260</WindowWidth>
  <WindowTopX>32767</WindowTopX>
  <WindowTopY>32767</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s64">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="4" ss:ExpandedRowCount="13" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
   <Column ss:Index="2" ss:AutoFitWidth="0" ss:Width="114"/>
   <Column ss:AutoFitWidth="0" ss:Width="117.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="90"/>
   <Row ss:Index="7">
    <Cell ss:MergeDown="6" ss:StyleID="s64"><Data ss:Type="String">DSM报警参数</Data></Cell>
    <Cell><Data ss:Type="String">报警使能速度阈值：</Data></Cell>
    <Cell><Data ss:Type="String">${test}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="2"><Data ss:Type="String">报警提示音量：</Data></Cell>
    <Cell><Data ss:Type="String">${volumeName}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="2" ss:MergeDown="3" ss:StyleID="s64"><Data ss:Type="String">拍照策略</Data></Cell>
    <Cell><Data ss:Type="String">主动拍照策略</Data></Cell>
    <Cell><Data ss:Type="String">${ageName}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="3"><Data ss:Type="String">主动定时拍照时间间隔</Data></Cell>
    <Cell><Data ss:Type="String">${volumeName}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="3"><Data ss:Type="String">每次主动拍照张数</Data></Cell>
    <Cell><Data ss:Type="String">${volumeName}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="3"><Data ss:Type="String">每次主动拍照时间间隔</Data></Cell>
    <Cell><Data ss:Type="String">${volumeName}</Data></Cell>
   </Row>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <TopRowVisible>6</TopRowVisible>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>9</ActiveRow>
     <ActiveCol>3</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
